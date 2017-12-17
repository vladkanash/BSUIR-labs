package lab1

sealed trait Word {

  def len: Int

  def contents: Set[Symbol]

  def allIn(seq: Set[Symbol]): Boolean = (contents -- seq).isEmpty

  def hasAnyIn(seq: Set[Symbol]): Boolean = (contents & seq).nonEmpty
}

case object EmptyWord extends Word {

  private val emptyWordRep = 'E'

  override val len = 0

  override val contents: Set[Symbol] = Set.empty

  override val toString: String = emptyWordRep.toString
}

case class SymbolicWord(value: CharSequence) extends Word {
  require(value.length() > 0, "word cannot be null")

  override val len: Int = value.length()

  override val contents: Set[Symbol] = value.toString.map(Symbol(_)).toSet

  override val toString: String = value.toString
}

object Word {

  val empty: Word = EmptyWord

  def apply(value: CharSequence) = SymbolicWord(value)

  implicit def symbolSetToWord(set: Set[Symbol]): Word = Word(set.mkString)
}




