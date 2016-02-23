package ft.transform

import ft.Complex

/**
  * Created by vladkanash on 2/12/16.
  */

object FFT extends GenericFT {

  private def butterfly(a: Complex, b: Complex, w: Complex): (Complex, Complex) =
    (a + (w * b), a - (w * b))

  private def butterflyRec(list: List[(Complex, Complex)],
                            wn: Complex,
                            w: Complex = Complex(1),
                            opCount: Int = 0,
                            resLeft: List[Complex] = List.empty,
                            resRight: List[Complex] = List.empty): (List[Complex], Int) =
    list match {
    case Nil => (resLeft.reverse ::: resRight.reverse, opCount)
    case head :: tail =>
      val butRes = butterfly(head._1, head._2, w)
      butterflyRec(tail, wn, w * wn, opCount + 2,  butRes._1 :: resLeft, butRes._2 :: resRight)
  }

  private def splitEvenOdd(xs: List[Complex]): (List[Complex], List[Complex]) = xs match {
    case e :: o :: xt =>
      val (es, os) = splitEvenOdd(xt)
      (e :: es, o :: os)
    case Nil => (Nil, Nil)
  }

  override def transform(list: List[Complex], dir: Boolean = false): FTResult = {
    val N: Double = list.length
    if (N <= 1) return FTResult(list)

    val (evenList, oddList) = splitEvenOdd(list)

    val evenResult = transform(evenList, dir)
    val oddResult = transform(oddList, dir)
    val wn = getW(1 / N, if (dir) 1 else -1)

    val (result, opCount) = butterflyRec(evenResult.resultList zip oddResult.resultList, wn)
    val finalOpCount = evenResult.additions + oddResult.additions + opCount
      FTResult(result.map(e => if (dir) e / Complex(2) else e), finalOpCount, finalOpCount)
  }
}