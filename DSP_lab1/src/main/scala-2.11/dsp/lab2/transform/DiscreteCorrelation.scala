package dsp.lab2.transform

import dsp.util.{RotatedIterator, Complex}

/**
  * Created by vladkanash on 3/15/16.
  */
object DiscreteCorrelation extends GenericSignalOperation {

  override def apply(sig1: List[Complex], sig2: List[Complex]): List[Complex] = {
    sig1.indices.map { m =>
      val rotatedSig2 = new RotatedIterator(sig2, m).toList
      (sig1 zip rotatedSig2).map(e => e._1 * e._2).sum
    }.toList
  }

}
