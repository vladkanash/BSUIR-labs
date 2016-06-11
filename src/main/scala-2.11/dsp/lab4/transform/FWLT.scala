package dsp.lab4.transform

import dsp.util.Complex

/**
  * Created by vladkanash on 2/12/16.
  */

object FWLT  extends GenericWLT {

  private def splitEvenOdd(xs: List[Complex]): (List[Complex], List[Complex]) = xs match {
    case e :: o :: xt =>
      val (es, os) = splitEvenOdd(xt)
      (e :: es, o :: os)
    case Nil => (Nil, Nil)
  }

  override def apply(list: List[Complex],
                     depth: Int,
                     dir: Boolean = false,
                     highs: List[List[Complex]] = List.empty): List[Complex] = {
    if (depth <= 0) list else
      if (!dir && list.length > 1) {
        val (even, odd) = splitEvenOdd(list)
        val result = even zip odd map { e =>
          ((e._1 + e._2)/2, (e._1 - e._2)/2)
        }
          apply(result.unzip._1, depth - 1, dir)
      } else {
        if (depth != highs.length || !dir) list else {
          val result = list zip highs.head flatMap { e =>
            List(e._1 + e._2, e._1 - e._2)
          }
          apply(result, depth - 1, dir, highs.tail)
        }
    }
  }

  def getHighs (list: List[Complex], depth: Int): List[Complex] = {
    if (list.length <= 1) list
    else {
      val (even, odd) = splitEvenOdd(list)
      val result = even zip odd map { e =>
        ((e._1 + e._2) / 2, (e._1 - e._2) / 2)
      }
      if (depth <= 1) result.unzip._2 else
        getHighs(result.unzip._1, depth - 1)
    }
  }
}