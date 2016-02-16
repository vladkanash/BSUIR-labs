import _root_.util._

/**
  * Created by vladkanash on 2/16/16.
  */
class DFTSpec extends FTSpec {

  "A discrete Fourier transform" should "produce the original result after reverse transform" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val out1 = DFT.transform(input)
    val output = DFT.transform(DFT.transform(input), dir = true)
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "produce the same signal as the fast transform does" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val fast_out = FFT.transform(input).map(truncate(_, 4))
    val disc_out = DFT.transform(input).map(truncate(_, 4))
    fast_out shouldEqual disc_out
  }
}
