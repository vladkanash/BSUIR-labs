import scala.math.{sin, cos, Pi}

/**
  * Created by vladkanash on 2/12/16.
  */

object FFT {

  private def get_w(n: Int, dir: Int) =
    Complex(cos(2 * Pi / n), dir * sin(2 * Pi / n))

  private def butterfly(a: Complex, b: Complex, w: Complex): (Complex, Complex) =
    (a + (w * b), a - (w * b))

  private def butterfly_rec(list: List[(Complex, Complex)],
                    wn: Complex,
                    w: Complex = Complex(1, 0),
                    res_left: List[Complex] = List.empty,
                    res_right: List[Complex] = List.empty): List[Complex] =
    list match {
    case Nil => res_left ++ res_right
    case head :: tail =>
      val but = butterfly(head._1, head._2, w)
      butterfly_rec(tail, wn, w * wn, res_left :+ but._1, res_right :+ but._2)
  }

  private def splitEvenOdd(xs: List[Complex]): (List[Complex], List[Complex]) = xs match {
    case e :: o :: xt =>
      val (es, os) = splitEvenOdd(xt)
      (e :: es, o :: os)
    case Nil => (Nil, Nil)
  }

  def transform(list: List[Complex], dir: Boolean = false): List[Complex] = {
    val N = list.length
    if (N == 1) return list

    val (evenList, oddList) = splitEvenOdd(list)

    val resultLists = transform(evenList, dir) zip transform(oddList, dir)
    val wn = get_w(N, if (dir) 1 else -1)

    butterfly_rec(resultLists, wn)
      .map(e => if (dir) e / Complex(2, 0) else e)
  }
}