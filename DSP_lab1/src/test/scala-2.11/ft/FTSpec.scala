package ft

import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

/**
  * Created by vladkanash on 2/16/16.
  */
trait FTSpec extends FlatSpec with Matchers with PrivateMethodTester {

  protected def truncate(e: Complex, s: Int): Complex =
    Complex(BigDecimal(e.real).setScale(s, BigDecimal.RoundingMode.HALF_UP).toDouble,
      BigDecimal(e.imag).setScale(s, BigDecimal.RoundingMode.HALF_UP).toDouble)
}

