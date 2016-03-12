package dsp.lab2

import dsp.lab1.transform.FFT
import dsp.util.Complex

/**
  * Created by vladkanash on 3/2/16.
  */
object Correlation extends GenericSignalOperation {

  override def apply(sig1: List[Complex], sig2: List[Complex]): List[Complex] = {
    val result = (FFT(sig1) zip FFT(sig2)).map(e => e._1.conjugate * e._2)
    FFT(result, dir = true)
  }
}
