package lab4

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

  val transitions: Set[StoreTransition] = ruleTransitions ++ terminalTransitions

  def parse(input: String): Unit = {

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
