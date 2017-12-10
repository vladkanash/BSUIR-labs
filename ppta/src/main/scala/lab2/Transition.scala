package lab2

import lab1.Symbol

case class Transition(inputState: State,
                      inputSymbol: Symbol,
                      outputState: State) {

  override val toString: String = s"($inputState, $inputSymbol) => $outputState"
}
