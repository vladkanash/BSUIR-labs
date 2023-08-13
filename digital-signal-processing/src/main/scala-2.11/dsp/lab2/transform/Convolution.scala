package dsp.lab2.transform

import dsp.lab1.transform.FFT
import dsp.util.Complex

/**
  * Created by vladkanash on 3/13/16.
  */
object Convolution extends GenericSignalOperation {

  override def apply(sig1: List[Complex], sig2: List[Complex]): List[Complex] = {
    val result = (FFT(sig1) zip FFT(sig2)).map(e => e._1 * e._2)
    FFT(result, dir = true)
  }
}
