package lab4

import java.util.regex.Pattern

import lab1.{Grammar, GrammarType, Symbol}
import lab2.State

class ExtendedAutomaticMemoryMachine (states: Set[State],
                                      inputs: Set[Symbol],
                                      storeSet: Set[Symbol],
                                      startState: State,
                                      endStates: Set[State],
                                      startStoreSymbol: Symbol,
                                      ruleTransitions: Set[StoreTransition],
                                      terminalTransitions: Set[StoreTransition])

  extends AutomaticMemoryMachine(states, inputs, storeSet, startState,
    endStates, startStoreSymbol, ruleTransitions, terminalTransitions) {

  override def parse(input: String): Unit = {

    def updateConfiguration(currentLevelConfigs: Set[Configuration], processedConfigs: Set[Configuration]): Boolean = {

      def updateStoreWithRuleTransition(store: String, tr: StoreTransition): String = store.mkString
        .replaceFirst(Pattern.quote(tr.inputStoreSymbols.reverse.mkString), tr.outputStoreSymbol.mkString)

      def finalConfig(config: Configuration) =
        (endStates contains config.state) && config.input.isEmpty

      def isRuleReachable(tr: StoreTransition, config: Configuration) =
        config.store.startsWith(tr.inputStoreSymbols.mkString.reverse) && !finalConfig(config) ||
        config.input.isEmpty && finalConfig(config)

      val childConfigs: Set[Configuration] = {

        val reachableRuleConfigs = currentLevelConfigs
          .flatMap { config => ruleTransitions
            .filter(tr => isRuleReachable(tr, config))
            .map { tr =>

              Configuration(config.index + 1, tr.outputState, config.input,
                updateStoreWithRuleTransition(config.store, tr), Some(config))
            }
          }

        val reachableTerminalConfigs = currentLevelConfigs
          .filterNot(config => config.input.isEmpty)
          .flatMap { config => terminalTransitions
            .filter(tr => tr.inputSymbol.value == config.input.head)
            .map { tr =>

              Configuration(config.index + 1, tr.outputState, config.input.tail,
                tr.inputSymbol + config.store, Some(config))
            }
          }
        reachableRuleConfigs ++ reachableTerminalConfigs
      }

      if (currentLevelConfigs.exists(_.index > searchThreshold)) false else
      if (childConfigs.isEmpty) false else
        currentLevelConfigs.find(finalConfig) match {
          case Some(finalConfig) => finalConfig.printParentList(reversed = true); true
          case None => updateConfiguration(childConfigs -- processedConfigs, processedConfigs ++ currentLevelConfigs)
        }
    }

    val startConfig = Configuration(0, startState, input, startStoreSymbol.toString, None)
    if (updateConfiguration(Set(startConfig), Set.empty)) {
      println("SUCCESS! This input string was successfully parsed with state machine")
    } else {
      println("ERROR! This input string cannot be parsed with this state machine")
    }
  }
}

object ExtendedAutomaticMemoryMachine {

  val endState = 'r'
  val extraStoreSymbol: Symbol = '#'

  def apply(grammar: Grammar): ExtendedAutomaticMemoryMachine = {
    require(grammar.grammarType == GrammarType.ContextFree, "Grammar type must be context-free to build state machine")

    val startState = State(AutomaticMemoryMachine.state)
    val endState = State(ExtendedAutomaticMemoryMachine.endState)
    val states = Set(startState, endState)
    val endStates = Set(endState)
    val startStoreSymbol = extraStoreSymbol
    val storeSet = grammar.terminals ++ grammar.nonTerminals + extraStoreSymbol
    val inputs = grammar.terminals

    val ruleTransitions = grammar.rules.map(rule =>
      StoreTransition(startState, Symbol.empty, rule.right.contents, startState, rule.left.contents))

    val terminalTransitions = grammar.terminals.map(terminal =>
      StoreTransition(startState, terminal, List(Symbol.empty), startState, List(terminal)))

    val finalTransition =
      StoreTransition(startState, Symbol.empty, List(extraStoreSymbol, grammar.startSymbol), endState, List(Symbol.empty))

    new ExtendedAutomaticMemoryMachine(
      states, inputs, storeSet, startState, endStates, startStoreSymbol, ruleTransitions + finalTransition, terminalTransitions)
  }
}
