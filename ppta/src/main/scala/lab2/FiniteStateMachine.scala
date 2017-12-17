package lab2

import lab1.Symbol

class FiniteStateMachine(params: StateMachineParams) {

  def this(states: Set[State], startState: State, endStates: Set[State],
           inputs: Set[Symbol], transitions: Set[Transition]) =
    this(StateMachineParams(states, startState, endStates, inputs, transitions))

  protected def newStateNames: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" diff params.states.mkString

  def states: Set[State] = params.states
  def startState: State = params.startState
  def endStates: Set[State] = params.endStates
  def inputs: Set[Symbol] = params.inputs
  def transitions: Set[Transition] = params.transitions

  override def toString: String =
    s"""Q (States): ${states.mkString}
       |T (Input Symbols): ${inputs.mkString}
       |F (Transitions): ${transitions.mkString("\n\t", ";\n\t", "")}
       |H (Start states): $startState
       |Z (End states): ${endStates.mkString}""".stripMargin
}