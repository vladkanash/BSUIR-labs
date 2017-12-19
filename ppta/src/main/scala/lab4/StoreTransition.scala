package lab4

import lab1.Symbol
import lab2.State

case class StoreTransition(inputState: State,
                           inputSymbol: Symbol,
                           inputStoreSymbols: List[Symbol],
                           outputState: State,
                           outputStoreSymbol: List[Symbol]) {

  override val toString: String =
    s"($inputState, $inputSymbol, '${inputStoreSymbols.mkString}') => ($outputState, '${outputStoreSymbol.mkString}')"

}
