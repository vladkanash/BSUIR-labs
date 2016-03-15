package dsp

/**
  * Created by vladkanash on 3/15/16.
  */
package object util {
  class RotatedIterator[A](seq: Seq[A], start: Int) extends Iterator[A] {
    var(before, after) = seq.splitAt(start)
    override def next = after match {
      case Seq() =>
        val (h :: t) = before
        before = t
        h
      case h :: t =>
        after = t
        h
    }

    override def hasNext = after.nonEmpty || before.nonEmpty
  }
}
