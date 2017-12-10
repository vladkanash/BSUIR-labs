package lab1

case class Rule(left: Word,
           right: Word) {

  override def toString: String = raw"$left => $right"

}
