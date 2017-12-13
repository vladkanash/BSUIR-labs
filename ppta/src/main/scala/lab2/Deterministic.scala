package lab2

import lab1.Symbol

trait Deterministic extends FiniteStateMachine {

  type TransitionsEntry = Map[Symbol, Set[State]]
  type TransitionsMap = Map[Set[State], TransitionsEntry]

  protected val extendedStateNames: String = newStateNames diff super.states.mkString

  private def determinationMap: TransitionsMap = {
    def getTransitionsColumn(dStates: Set[State]): TransitionsEntry =
      inputs.map(input => input -> dStates.flatMap(getReachableStates(_, input))).toMap

    def getReachableStates(state: State, input: Symbol) =
      super.transitions.filter(t => (t.inputSymbol == input) && (t.inputState == state)).map(_.outputState)

    def createNewStates(processed: TransitionsMap, newStates: Set[Set[State]]): TransitionsMap = {
      val newColumns = newStates.map(state => state -> getTransitionsColumn(state)).toMap
      val filteredColumns = newColumns.filterNot(col => processed.contains(col._1))
      val createdStates = filteredColumns.values.flatten.map(_._2).filterNot(_.isEmpty).toSet
      if (filteredColumns.isEmpty) processed else createNewStates(processed ++ filteredColumns, createdStates)
    }

    createNewStates(Map.empty, Set(Set(startState)))
  }
  private def rawNewStates = determinationMap.keySet.filter(_.size > 1)
  private def newStatesMap = rawNewStates.zip(extendedStateNames.map(State(_))).toMap.withDefault(_.head)

  override def states: Set[State] = super.states ++ rawNewStates.map(newStatesMap)

  override def endStates: Set[State] = super.endStates ++
    rawNewStates.filterNot(s => (s & super.endStates).isEmpty).map(newStatesMap)

  override def transitions: Set[Transition] = determinationMap.flatMap(entry =>
      entry._2.filterNot(cell => cell._2.isEmpty)
        .map(cell => Transition(newStatesMap(entry._1), cell._1, newStatesMap(cell._2)))).toSet

}
