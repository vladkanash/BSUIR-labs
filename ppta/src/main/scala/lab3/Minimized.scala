package lab3

import lab2.{FiniteStateMachine, State, Transition}
import lab1.Symbol

trait Minimized extends FiniteStateMachine {

  override def newStateNames: String = minimizedNewStateNames

  private val minimizedNewStateNames = super.newStateNames diff super.states.mkString

  private val reachableStates: Set[State] = {

    def extendStates(reachedStates: Set[State], newStates: Set[State]): Set[State] = {
        val reached = newStates.flatMap(state =>
        super.transitions.filter(_.inputState == state).map(_.outputState)) ++ newStates -- reachedStates
      if (reached.isEmpty) reachedStates else extendStates(reachedStates ++ reached, reached)
    }

    extendStates(Set.empty, Set(super.startState))
  }

  private val uniqueStatesMap: Map[State, State] = {

    def getReachMap(state: State, classes: Array[Set[State]]): Map[Symbol, Int] = super.transitions
      .filter(_.inputState == state)
      .map(transition => transition.inputSymbol ->
        classes.indexOf(classes.find(_.contains(transition.outputState)).get))
      .toMap

    def extendClasses(classes: Set[Set[State]]): Set[Set[State]] = {
      val newClasses = classes.flatten.groupBy((state: State) => getReachMap(state, classes.toArray)).values.toSet
      if (classes == newClasses) newClasses else extendClasses(newClasses)
    }

    val stateClasses = extendClasses(reachableStates.partition(super.endStates.contains)
      .productIterator.map(_.asInstanceOf[Set[State]]).toSet)

    stateClasses.filter(_.size > 1).zip(minimizedNewStateNames.map(State(_)))
      .flatMap(states => states._1.map(s => (s, states._2)))
      .toMap.withDefault(identity)
  }

  override def states: Set[State] = super.states intersect reachableStates map uniqueStatesMap

  override def endStates: Set[State] = super.endStates intersect reachableStates map uniqueStatesMap

  override def startState: State = uniqueStatesMap(super.startState)

  override def transitions: Set[Transition] = super.transitions
    .filter(tr => reachableStates.contains(tr.inputState) && reachableStates.contains(tr.outputState))
    .map(tr => Transition(uniqueStatesMap(tr.inputState), tr.inputSymbol, uniqueStatesMap(tr.outputState)))
}
