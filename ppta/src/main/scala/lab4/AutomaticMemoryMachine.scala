package lab4

import java.util.regex.Pattern

import lab1.{Grammar, GrammarType, Symbol}
import lab2.State

class AutomaticMemoryMachine(val states: Set[State],
                             val inputs: Set[Symbol],
                             val storeSet: Set[Symbol],
                             val startState: State,
                             val endStates: Set[State],
                             val startStoreSymbol: Symbol,
                             val ruleTransitions: Set[StoreTransition],
                             val terminalTransitions: Set[StoreTransition]) {

  val searchThreshold = 40

  val transitions: Set[StoreTransition] = ruleTransitions ++ terminalTransitions

  def parse(input: String, verbose: Boolean = false): Boolean = {

    def updateConfiguration(currentLevelConfigs: Set[Configuration], processedConfigs: Set[Configuration]): Boolean = {

      def updateStoreWithRuleTransition(store: String, tr: StoreTransition): String = store
        .replaceFirst(Pattern.quote(tr.inputStoreSymbols.mkString), tr.outputStoreSymbol.mkString)

      def finalConfig(config: Configuration): Boolean =
        config.input.head == config.store.head &&
        config.input.length == config.store.length &&
        config.input.length == 1

      val childConfigs: Set[Configuration] = {

        val reachableRuleConfigs = currentLevelConfigs
          .flatMap { config => ruleTransitions
            .filter(tr => config.store.head == tr.inputStoreSymbols.mkString.head)
            .map { tr =>

              Configuration(config.index + 1, tr.outputState, config.input,
                updateStoreWithRuleTransition(config.store, tr), Some(config))
            }
          }

        val reachableTerminalConfigs = currentLevelConfigs
          .filterNot(config => config.input.isEmpty || config.store.isEmpty)
          .filter(config => config.input.head == config.store.head)
          .flatMap { config =>terminalTransitions
            .filter(tr => tr.inputSymbol.value == config.store.head)
            .map { tr =>

              Configuration(config.index + 1, tr.outputState, config.inputTail,
                config.storeTail, Some(config))
            }
          }

        reachableRuleConfigs ++ reachableTerminalConfigs
      }

//      currentLevelConfigs foreach println

      if (currentLevelConfigs.exists(_.index > searchThreshold)) false else
      if (childConfigs.isEmpty) false else
        currentLevelConfigs.find(finalConfig) match {
          case Some(finalConfig) => if (verbose) finalConfig.printParentList(); true
          case None => updateConfiguration(childConfigs -- processedConfigs, processedConfigs ++ currentLevelConfigs)
        }
    }

    val startConfig = Configuration(0, startState, input, startStoreSymbol.toString, None)
    updateConfiguration(Set(startConfig), Set.empty)
  }

  override val toString: String =
    s"""Q (States): ${states.mkString}
       |T (Input Symbols): ${inputs.mkString}
       |N (Input Store Symbols): ${storeSet.mkString}
       |F (Transitions): ${transitions.mkString("\n\t", ";\n\t", ";")}
       |q0 (Start state): $startState
       |N0 (Start store symbol): $startStoreSymbol
       |Z (End states): ${endStates.mkString}""".stripMargin
}

object AutomaticMemoryMachine {

  val state = 'q'

  def apply(grammar: Grammar): AutomaticMemoryMachine = {
    require(grammar.grammarType == GrammarType.ContextFree, "Grammar type must be context-free to build state machine")

    val state = State(AutomaticMemoryMachine.state)
    val states = Set(state)
    val startState = state
    val endStates = Set.empty[State]
    val startStoreSymbol = grammar.startSymbol
    val inputs = grammar.terminals
    val storeSet = grammar.terminals ++ grammar.nonTerminals

    val ruleTransitions = grammar.rules.map(rule =>
      StoreTransition(state, Symbol.empty, rule.left.contents, state, rule.right.contents))

    val terminalTransitions = grammar.terminals.map(terminal =>
      StoreTransition(state, terminal, List(terminal), state, List(Symbol.empty)))

    new AutomaticMemoryMachine(
      states, inputs, storeSet, startState, endStates, startStoreSymbol, ruleTransitions, terminalTransitions)
  }
}
