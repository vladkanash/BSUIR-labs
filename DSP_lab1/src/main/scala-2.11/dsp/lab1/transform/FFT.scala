package dsp.lab1.transform

import dsp.util.Complex


/**
  * Created by vladkanash on 2/12/16.
  */

object FFT extends GenericFT {

  private def butterfly(a: Complex, b: Complex, w: Complex): (Complex, Complex) =
    (a + (w * b), a - (w * b))

  private def butterflyRec(list: List[(Complex, Complex)],
                            wn: Complex,
                            w: Complex = 1,
                            resLeft: List[Complex] = List.empty,
                            resRight: List[Complex] = List.empty): List[Complex] =
    list match {
    case Nil => resLeft.reverse ::: resRight.reverse
    case head :: tail =>
      val butRes = butterfly(head._1, head._2, w)
      butterflyRec(tail, wn, w * wn, butRes._1 :: resLeft, butRes._2 :: resRight)
  }

  private def splitEvenOdd(xs: List[Complex]): (List[Complex], List[Complex]) = xs match {
    case e :: o :: xt =>
      val (es, os) = splitEvenOdd(xt)
      (e :: es, o :: os)
    case Nil => (Nil, Nil)
  }

  override def apply(list: List[Complex], dir: Boolean = false): List[Complex] = list match {
    case head :: Nil => list
    case _ =>
      val N = list.length

      val (evenList, oddList) = splitEvenOdd(list)

      val evenResult = apply(evenList, dir)
      val oddResult = apply(oddList, dir)
      val wn = getW(1 / N.toDouble, if (dir) 1 else -1)

      butterflyRec(evenResult zip oddResult, wn)
        .map(e => if (dir) e / 2 else e)
  }

  override def getMultiplicationsCount(N: Int): Int = (N * math.log2(N)).toInt

  override def getAdditionsCount(N: Int): Int = (N * math.log2(N)).toInt
}