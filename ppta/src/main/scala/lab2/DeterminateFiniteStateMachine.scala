package lab2

import lab1.{Grammar, Symbol}

class DeterminateFiniteStateMachine(grammar: Grammar) extends FiniteStateMachine(grammar: Grammar) {

  type TransitionsEntry = Map[Symbol, Set[State]]
  type TransitionsMap = Map[Set[State], TransitionsEntry]

  private val deteminationMap: TransitionsMap = {
    def getTransitionsColumn(dStates: Set[State]): TransitionsEntry =
      inputs.map(input => input -> dStates.flatMap(getReachableStates(_, input))).toMap

    def getReachableStates(state: State, input: Symbol) =
      transitions.filter(t => (t.inputSymbol == input) && (t.inputState == state)).map(_.outputState)

    def createNewStates(processed: TransitionsMap, newStates: Set[Set[State]]): TransitionsMap = {
      val newColumns = newStates.map(state => state -> getTransitionsColumn(state)).toMap
      val filteredColumns = newColumns.filterNot(col => processed.contains(col._1))
      val createdStates = filteredColumns.values.flatten.map(_._2).filterNot(_.isEmpty).toSet
      if (filteredColumns.isEmpty) processed else createNewStates(processed ++ filteredColumns, createdStates)
    }

    createNewStates(Map.empty, Set(Set(startState)))
  }

  private val rawNewStates = deteminationMap.keySet.filter(_.size > 1)
  private val newStateNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" diff states.mkString take rawNewStates.size
  private val newStatesMap = rawNewStates.zip(newStateNames.map(State(_))).toMap.withDefault(_.head)

  val determinateStates: Set[State] = states ++ rawNewStates.map(newStatesMap)

  val determinateEndStates: Set[State] = endStates ++
    rawNewStates.filterNot(s => (s & endStates).isEmpty).map(newStatesMap)

  val determinateTransitions: Set[Transition] = deteminationMap.flatMap(entry =>
      entry._2.filterNot(cell => cell._2.isEmpty)
        .map(cell => Transition(newStatesMap(entry._1), cell._1, newStatesMap(cell._2)))).toSet

//  override val toString: String =
//    s"""Q (States): ${determinateStates.mkString}
//       |T (Input Symbols): ${inputs.mkString}
//       |F (Transitions): ${determinateTransitions.mkString("\n\t", ";\n\t", "")}
//       |H (Start states): $startState
//       |Z (End states): ${determinateEndStates.mkString}""".stripMargin

}
