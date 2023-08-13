package dsp.util

/**
  * Created by vladkanash on 2/12/16.
  */

import scala.math.{atan2, cos, sin, sqrt}

abstract class Complex extends Equals {
  val real:Double
  val imag:Double
  val magnitude:Double
  val angle:Double

  def + (other:Complex) = RectComplex(real + other.real, imag + other.imag)
  def - (other:Complex) = RectComplex(real - other.real, imag - other.imag)
  def * (other:Complex) =
    Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real)
  def / (other:Complex) =
    PolarComplex(magnitude / other.magnitude, angle - other.angle)
  def conjugate = RectComplex(real, -imag)
  def * = conjugate
  def unary_- = RectComplex(-real, -imag)

  override def toString = "("+real+", "+imag+" ["+magnitude+"e"+angle+"theta])"

  override def equals (other:Any) = {
    other match {
      case that: Complex =>
        that.canEqual(this) && real == that.real && imag == that.imag
      case _ =>
        false
    }
  }

  override def canEqual(other:Any) = {
    other.isInstanceOf[Complex]
  }

  override def hashCode = {
    41 * (41 + real.toInt) + imag.toInt
  }
}

class RectComplex(val real:Double, val imag:Double) extends Complex {

  private def truncate(e: Double, s: Int = 5): Double =
    BigDecimal(e).setScale(s, BigDecimal.RoundingMode.HALF_UP).toDouble

  val magnitude = sqrt(real*real + imag*imag)
  val angle:Double = atan2(truncate(imag), truncate(real))

}

class PolarComplex(val magnitude:Double, val angle:Double) extends Complex {
  val real = magnitude * cos(angle)
  val imag = magnitude * sin(angle)
}

object Complex extends Numeric[Complex] {

  def plus(x: Complex, y: Complex): Complex = x + y
  def minus(x: Complex, y: Complex): Complex = x - y
  def times(x: Complex, y: Complex): Complex = x * y
  def negate(x: Complex): Complex = -x
  def fromInt(x: Int): Complex = x.toDouble
  def toInt(x: Complex): Int = x.toInt
  def toLong(x: Complex): Long = x.toLong
  def toFloat(x: Complex): Float = x.toFloat
  def toDouble(x: Complex): Double = x.toDouble

  override def compare(x: Complex, y: Complex): Int = compare(x.real, y.real)

  override def zero = fromInt(0)
  override def one = fromInt(1)

  // Default to the more-common rectangular version
  def apply(real:Double, imag:Double = 0) = new RectComplex(real, imag)

  implicit def double2complex(real:Double):Complex = Complex(real, 0)
  implicit def int2complex(real: Int):Complex = Complex(real, 0)
  implicit def doubleList2complexList(list: List[Double]):List[Complex] = list.map(double2complex)
  implicit def intList2complexList(list: List[Int]): List[Complex] = list.map(int2complex)
  implicit def complex2double(complex: Complex): Double = complex.real
  implicit def complexList2doubleList(list: List[Complex]): List[Double] = list.map(complex2double)
}

object RectComplex {
  def apply(real:Double, imag:Double = 0) = {
    new RectComplex(real, imag)
  }
}

object PolarComplex extends {
  def apply(magnitude:Double, angle:Double = 0) = {
    new PolarComplex(magnitude, angle)
  }
}
