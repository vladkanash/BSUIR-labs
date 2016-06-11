package dsp.lab1.transform

/**
  * Created by vladkanash on 2/16/16.
  */

import dsp.GenericSpec
import dsp.util.Complex

import scala.math._

class DFTSpec extends GenericSpec {

  "A discrete Fourier transform" should "produce the original result after reverse transform" in {
    val input: List[Complex] = (0 until 8).toList
    val output = DFT(DFT(input), dir = true)
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "work in more complex cases" in {
    val input = (0 until 16)
      .map(e => sin(2 * e) + 3 * cos(e))
      .map(truncate(_, 8)).toList
    val output = DFT(DFT(input), dir = true)
      .map(truncate(_, 8))
    output shouldEqual input
  }

  it should "produce the same signal as the fast transform does" in {
    val input = (0 until 8).toList
    val fast_out = FFT(input).map(truncate(_, 4))
    val disc_out = DFT(input).map(truncate(_, 4))
    fast_out shouldEqual disc_out
  }

  it should "calculate multiplications count during the transform" in {
    val multiplicationCount = DFT.getMultiplicationsCount(32)
    multiplicationCount shouldEqual 32 * 32
  }
}
