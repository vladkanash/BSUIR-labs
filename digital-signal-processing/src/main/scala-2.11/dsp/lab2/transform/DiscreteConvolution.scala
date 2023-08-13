package dsp.lab2.transform

import dsp.util.{RotatedIterator, Complex}

/**
  * Created by vladkanash on 3/15/16.
  */
object DiscreteConvolution extends GenericSignalOperation {

  override def apply(sig1: List[Complex], sig2: List[Complex]): List[Complex] = {
    val N = sig1.length

    (0 until N).map { m =>
      val rotatedSig2 = new RotatedIterator(sig2, m + 1).toList.reverse
      (sig1 zip rotatedSig2).map(e => e._1 * e._2).foldLeft(Complex(0)) {
        (result, elem) => result + elem
      }
    }.toList
  }

}
