package lab4

import lab2.State

case class Configuration(index: Integer, state: State, input: String, store: String, parent: Option[Configuration]) {

  val inputTail: String =
    if (input.isEmpty) "" else
    if (input.length > 1) input.tail else input.last.toString

  val storeTail: String =
    if (store.isEmpty) "" else
    if (store.length > 1) store.tail else store.last.toString

  def printParentList(reversed: Boolean = false): Unit = {
    println(Configuration.header)
    if (reversed)
      collectParent(List(this)) foreach(conf => println(conf.reversedString(true)))
    else
      collectParent(List(this)) foreach println
  }

  private def collectParent(collected: List[Configuration]): List[Configuration] = parent match {
    case Some(conf) => conf.collectParent(conf :: collected)
    case None => collected
  }

  def reversedString(reversed: Boolean): String  =
    f"$index%3s [ $input%15s | $state%5s | ${if (reversed) store.mkString.reverse else store.mkString}%15s ]"

  override val toString: String = reversedString(false)
}

object Configuration {
  val header = f"  №             Input   State             Store"
}
