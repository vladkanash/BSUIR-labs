package lab2

import lab1.Symbol

case class StateMachineParams(states : Set[State],
                              startState: State,
                              endStates: Set[State],
                              inputs: Set[Symbol],
                              transitions: Set[Transition])
