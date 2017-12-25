package lab1

sealed trait Word {

  def len: Int
  def contents: List[Symbol]
  def allIn(seq: Set[Symbol]): Boolean = (contents.toSet -- seq).isEmpty
  def hasAnyIn(seq: Set[Symbol]): Boolean = (contents.toSet & seq).nonEmpty
}

case object EmptyWord extends Word {

  private val emptyWordRep = 'ε'

  override val len = 0
  override val contents: List[Symbol] = List.empty
  override val toString: String = emptyWordRep.toString
}

case class SymbolicWord(value: CharSequence) extends Word {
  require(value.length() > 0, "word cannot be null")

  override val len: Int = value.length()
  override val contents: List[Symbol] = value.toString.map(Symbol(_)).toList
  override val toString: String = value.toString
}

object Word {

  val empty: Word = EmptyWord

  def apply(value: CharSequence) = SymbolicWord(value)

  implicit def symbolSetToWord(set: Set[Symbol]): Word = Word(set.mkString)
  implicit def symbolListToWord(list: List[Symbol]): Word = Word(list.mkString)
  implicit def stringToWord(str: String): Word = Word(str)
}




