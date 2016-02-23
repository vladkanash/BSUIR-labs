package ft.transform

/**
  * Created by vladkanash on 2/16/16.
  */

import ft.util._

import scala.math._

class DFTSpec extends FTSpec {

  "A discrete Fourier transform" should "produce the original result after reverse transform" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val output = DFT.transform(DFT.transform(input).resultList, dir = true).resultList
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "work in more complex cases" in {
    val input = (0 until 16)
      .map(e => sin(2 * e) + 3 * cos(e))
      .map(double2complex)
      .map(truncate(_, 8)).toList
    val output = DFT.transform(DFT.transform(input).resultList, dir = true).resultList
      .map(truncate(_, 8))
    output shouldEqual input
  }

  it should "produce the same signal as the fast transform does" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val fast_out = FFT.transform(input).resultList.map(truncate(_, 4))
    val disc_out = DFT.transform(input).resultList.map(truncate(_, 4))
    fast_out shouldEqual disc_out
  }

  it should "calculate multiplications count during the transform" in {
    val input = (0 until 32)
      .map(e => 2 * sin(3 * e) + 2 * cos(e) - 7)
      .map(double2complex).toList
    val multiplicationCount = DFT.transform(input).multiplications

    multiplicationCount shouldEqual 32 * 32
  }
}
