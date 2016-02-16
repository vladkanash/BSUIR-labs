package ft

import scala.math._

/**
  * Created by vladkanash on 2/12/16.
  */

object FFT extends GenericFT {

  private def get_w(n: Int, dir: Int) =
    Complex(cos(2 * Pi / n), dir * sin(2 * Pi / n))

  private def butterfly(a: Complex, b: Complex, w: Complex): (Complex, Complex) =
    (a + (w * b), a - (w * b))

  private def butterfly_rec(list: List[(Complex, Complex)],
                            wn: Complex,
                            w: Complex = Complex(1, 0),
                            op_count: Int = 0,
                            res_left: List[Complex] = List.empty,
                            res_right: List[Complex] = List.empty): (List[Complex], Int) =
    list match {
    case Nil => (res_left.reverse ::: res_right.reverse, op_count)
    case head :: tail =>
      val but = butterfly(head._1, head._2, w)
      butterfly_rec(tail, wn, w * wn, op_count + 2,  but._1 :: res_left, but._2 :: res_right)
  }

  private def splitEvenOdd(xs: List[Complex]): (List[Complex], List[Complex]) = xs match {
    case e :: o :: xt =>
      val (es, os) = splitEvenOdd(xt)
      (e :: es, o :: os)
    case Nil => (Nil, Nil)
  }

  def transform(list: List[Complex], dir: Boolean = false): FTResult = {
    val N = list.length
    if (N == 1) return FTResult(list)

    val (evenList, oddList) = splitEvenOdd(list)

    val evenResult = transform(evenList, dir)
    val oddResult = transform(oddList, dir)
    val wn = get_w(N, if (dir) 1 else -1)

    val (result, op_count) = butterfly_rec(evenResult.result zip oddResult.result, wn)
    val finalOpCount = evenResult.additions + oddResult.additions + op_count
      FTResult(result.map(e => if (dir) e / Complex(2, 0) else e), finalOpCount, finalOpCount)
  }
}