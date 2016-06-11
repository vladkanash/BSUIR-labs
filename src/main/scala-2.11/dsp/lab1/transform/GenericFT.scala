package dsp.lab1.transform

import dsp.util.Complex

import scala.math.{Pi, cos, sin}

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericFT {

  protected def getW(arg: Double, dir: Int) =
    Complex(cos(2 * Pi * arg), dir * sin(2 * Pi * arg))

  def apply(list: List[Complex], dir: Boolean = false): List[Complex]

  def getMultiplicationsCount(N: Int): Int

  def getAdditionsCount(N: Int): Int
}
