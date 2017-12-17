package lab2

import lab1.Symbol

case class Transition(inputState: State, inputSymbol: Symbol, outputState: State) {

  def this(input: String) = this(State(input.charAt(0)), Symbol(input.charAt(1)), State(input.charAt(2)))

  override val toString: String = s"($inputState, $inputSymbol) => $outputState"
}
