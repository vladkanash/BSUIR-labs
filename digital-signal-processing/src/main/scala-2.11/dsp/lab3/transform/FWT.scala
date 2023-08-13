package dsp.lab3.transform

import dsp.util.Complex

/**
  * Created by vladkanash on 2/12/16.
  */

object FWT extends GenericWT {

  private def butterfly(a: Complex, b: Complex): (Complex, Complex) =
    (a + b, a - b)

  private def butterflyRec(list: List[(Complex, Complex)],
                            resLeft: List[Complex] = List.empty,
                            resRight: List[Complex] = List.empty): (List[Complex], List[Complex]) =
    list match {
    case Nil => (resLeft.reverse, resRight.reverse)
    case head :: tail =>
      val butRes = butterfly(head._1, head._2)
      butterflyRec(tail, butRes._1 :: resLeft, butRes._2 :: resRight)
  }

  override def apply(list: List[Complex], dir: Boolean = false): List[Complex] = list match {
    case head :: Nil => list
    case _ =>
      val N = list.length
      val (leftList, rightList) = (list.take(N / 2), list.drop(N / 2))
      val (leftRes, rightRes) = butterflyRec(leftList zip rightList)

      val left = apply(leftRes, dir)
      val right = apply(rightRes, dir)

       left ::: right
        .map(e => if (!dir) e / 2 else e)
  }
}