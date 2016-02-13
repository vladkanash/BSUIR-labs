import org.scalatest.{Matchers, PrivateMethodTester, FlatSpec}
import util.double2complex
import scala.math.{sin, cos}

/**
  * Created by vladkanash on 2/12/16.
  */

class FFTSpec extends FlatSpec with PrivateMethodTester with Matchers {

  def truncate(e: Complex, s: Int): Complex =
    Complex(BigDecimal(e.real).setScale(s, BigDecimal.RoundingMode.HALF_UP).toDouble,
      BigDecimal(e.imag).setScale(s, BigDecimal.RoundingMode.HALF_UP).toDouble)

  "A fast transform" should "produce the original signal after reverse transform" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val output = FFT.transform(FFT.transform(input), dir = true)
      .map(truncate(_, 4))
    input shouldEqual output
  }

  it should "work in more complex cases" in {
    val input = (0 until 16)
      .map(e => sin(2 * e) + 3 * cos(e))
      .map(double2complex)
      .map(truncate(_, 8)).toList
    val output = FFT.transform(FFT.transform(input), dir = true)
      .map(truncate(_, 8))
    output shouldEqual input
  }

  "A recursive butterfly function" should "produce the correct result" in {
    val butterfly_rec = PrivateMethod[List[Complex]]('butterfly_rec)
    val input = (1 to 5).map(e => (double2complex(e), double2complex(e))).toList
    val output = FFT invokePrivate butterfly_rec(input, Complex(1, 0), Complex(1, 0), List.empty, List.empty)
    val excepted = List(2, 4, 6, 8, 10, 0, 0, 0, 0, 0).map(double2complex(_))
    output shouldEqual excepted
  }

}
