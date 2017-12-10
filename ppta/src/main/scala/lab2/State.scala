package lab2

import lab1.Symbol

case class State(value: Char) {

  override def toString: String = value.toString

}

object State {
  implicit def symbolToState(symbol: Symbol): State = State(symbol.value)
  implicit def symbolsToStates(symbols: Set[Symbol]): Set[State] = symbols.map(symbolToState)
}
