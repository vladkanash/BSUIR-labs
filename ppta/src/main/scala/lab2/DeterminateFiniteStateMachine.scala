package lab2

import lab1.{Grammar, Symbol}

class DeterminateFiniteStateMachine(grammar: Grammar) extends FiniteStateMachine(grammar: Grammar) {

  type TransitionsColumn = Map[Symbol, Set[State]]
  type TransitionsTable = Map[Set[State], TransitionsColumn]

  private val determinationTable: TransitionsTable = {
    def getTransitionsColumn(dStates: Set[State]): TransitionsColumn =
      inputs.map(input => input -> dStates.flatMap(getReachableStates(_, input))).toMap

    def getReachableStates(state: State, input: Symbol) =
      transitions
        .filter(_.inputSymbol == input)
        .filter(_.inputState == state)
        .map(_.outputState)

    def createNewStates(processed: TransitionsTable, newStates: Set[Set[State]]): TransitionsTable = {
      val newColumns = newStates.map(state => state -> getTransitionsColumn(state)).toMap
      val filteredColumns = newColumns.filterNot(col => processed.contains(col._1))
      val createdStates = filteredColumns.values.flatten.map(_._2).filterNot(_.isEmpty).toSet
      if (filteredColumns.isEmpty) processed else createNewStates(processed ++ filteredColumns, createdStates)
    }

    createNewStates(Map.empty, Set(Set(startState)))
  }

  val determinateStates: Set[State] = ???
  val determinateEndStates: Set[State] = determinateStates.filter(_ => false)
  val determinateTransitions: Set[Transition] = ???

}
