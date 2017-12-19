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

    println(f"  №             Input   State             Store")

    def updateConfiguration(confIdx: Int, state: State, input: String, store: String): Boolean = {
      println(f"$confIdx%3s [ $input%15s | $state%5s | ${store.mkString.reverse}%15s ]")

      def isFinal(tr: StoreTransition) = endStates contains tr.outputState

      if (endStates contains state) true else {

        ruleTransitions
          .find(tr =>
            store.startsWith(tr.inputStoreSymbols.mkString.reverse) && !isFinal(tr) ||
            input.isEmpty && isFinal(tr)) match {

            case Some(tr) =>
                val updatedStore = store.mkString
                  .replaceFirst(Pattern.quote(tr.inputStoreSymbols.reverse.mkString), tr.outputStoreSymbol.mkString)
                updateConfiguration(confIdx + 1, tr.outputState, input, updatedStore)

            case None =>
              if (input.isEmpty) false else {
                terminalTransitions.find(tr => tr.inputSymbol.value == input.head) match {
                  case Some(tr) => updateConfiguration(confIdx + 1, tr.outputState, input.tail, tr.inputSymbol + store)
                  case None => false
                }
              }
          }
      }
    }

    if (updateConfiguration(0, startState, input, startStoreSymbol.toString)) {
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
