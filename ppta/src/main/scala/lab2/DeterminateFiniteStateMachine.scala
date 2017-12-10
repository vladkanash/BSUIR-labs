package lab2

import lab1.{Grammar, Symbol}

class DeterminateFiniteStateMachine(grammar: Grammar) extends FiniteStateMachine(grammar: Grammar) {

  type TransitionsColumn = Set[(Symbol, Set[State])]
  type TransitionsTable = Set[(Set[State], TransitionsColumn)]

  private val determinateTable: TransitionsTable = {
    def getTransitionsColumn(dStates: Set[State]): TransitionsColumn =
      inputs.map(in => (in, dStates.flatMap(getReachableStates(_, in))))

    def getReachableStates(state: State, input: Symbol) =
      transitions
        .filter(_.inputSymbol == input)
        .filter(_.inputState == state)
        .map(_.outputState)

    def createNewStates(processed: TransitionsTable, newStates: Set[Set[State]]): TransitionsTable = {
      val newColumns = newStates.map(state => (state, getTransitionsColumn(state)))
      val filteredColumns = newColumns.filterNot(col => processed.map(_._1).contains(col._1))
      val createdStates = filteredColumns.flatMap(col => col._2.filterNot(_._2.isEmpty).map(_._2))
      if (filteredColumns.isEmpty) processed else createNewStates(processed ++ filteredColumns, createdStates)
    }

    createNewStates(Set.empty, Set(Set(startState)))
  }

  val determinateStates: Set[State] = ???
  val determinateEndStates: Set[State] = determinateStates.filter(_ => false)
  val determinateTransitions: Set[Transition] = ???

}
