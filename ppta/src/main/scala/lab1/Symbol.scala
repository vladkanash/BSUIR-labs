package lab1

case class Symbol(value: Char) {

  override def toString: String = value.toString
}

object Symbol {
  implicit def charToSymbol(c: Char): Symbol = Symbol(c)
}