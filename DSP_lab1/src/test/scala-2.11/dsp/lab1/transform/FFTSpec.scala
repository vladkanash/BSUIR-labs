package dsp.lab1.transform

import dsp.GenericSpec
import dsp.util.Complex

import scala.math.{cos, sin}

/**
  * Created by vladkanash on 2/12/16.
  */

class FFTSpec extends GenericSpec {


  "A fast transform" should "produce the original signal after reverse transform" in {
    val input: List[Complex] = (0 until 8).toList
    val output = FFT(FFT(input), dir = true)
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "work in more complex cases" in {
    val input: List[Complex] = (0 until 16)
      .map(e => sin(2 * e) + 3 * cos(e))
      .map(truncate(_, 4)).toList
    val output = FFT(FFT(input), dir = true)
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "calculate the additions count during the transform" in {
    val additionsCount = FFT.getAdditionsCount(32)
    additionsCount shouldEqual 160
  }

  "A recursive butterfly function" should "produce the correct result" in {
    val butterflyRec = PrivateMethod[List[Complex]]('butterflyRec)
    val input = (1 to 5).map(e => (Complex(e), Complex(e))).toList
    val output = FFT invokePrivate butterflyRec(input, Complex(1), Complex(1), List.empty, List.empty)
    val excepted: List[Complex] = List(2, 4, 6, 8, 10, 0, 0, 0, 0, 0)
    output shouldEqual excepted
  }

}
