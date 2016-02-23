package ft.transform

import ft.Complex
import ft.util.double2complex

import scala.math.{cos, sin}

/**
  * Created by vladkanash on 2/12/16.
  */

class FFTSpec extends FTSpec {


  "A fast transform" should "produce the original signal after reverse transform" in {
    val input = (0 until 8).map(double2complex(_)).toList
    val output = FFT.transform(FFT.transform(input).resultList, dir = true).resultList
      .map(truncate(_, 4))
    output shouldEqual input
  }

  it should "work in more complex cases" in {
    val input = (0 until 16)
      .map(e => sin(2 * e) + 3 * cos(e))
      .map(double2complex)
      .map(truncate(_, 8)).toList
    val output = FFT.transform(FFT.transform(input).resultList, dir = true).resultList
      .map(truncate(_, 8))
    output shouldEqual input
  }

  it should "calculate the additions count during the transform" in {
    val input = (0 until 32)
      .map(e => sin(5 * e) + 3 * cos(e) + 5)
      .map(double2complex).toList
    val additionsCount = FFT.transform(input).additions

    additionsCount shouldEqual 160
  }

  "A recursive butterfly function" should "produce the correct result" in {
    val butterflyRec = PrivateMethod[(List[Complex], Int)]('butterflyRec)
    val input = (1 to 5).map(e => (double2complex(e), double2complex(e))).toList
    val output = FFT invokePrivate butterflyRec(input, Complex(1, 0), Complex(1, 0), 0, List.empty, List.empty)
    val excepted = List(2, 4, 6, 8, 10, 0, 0, 0, 0, 0).map(double2complex(_))
    output._1 shouldEqual excepted
  }

}
