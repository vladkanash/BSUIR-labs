package dsp.lab2.transform

import dsp.util.Complex

/**
  * Created by vladkanash on 3/13/16.
  */
trait GenericSignalOperation {

  val N = 128
  def generateList(sig: Complex => Complex):List[Complex] = (0 until N).map(n => sig(n * 1.0 / N)).toList

  def apply(sig1: List[Complex], sig2: List[Complex]): List[Complex]

  def apply(sig1: Complex => Complex, sig2: Complex => Complex, N: Int = 128): List[Complex] = {
    val input1 = generateList(sig1)
    val input2 = generateList(sig2)
    apply(input1, input2)
  }
}
