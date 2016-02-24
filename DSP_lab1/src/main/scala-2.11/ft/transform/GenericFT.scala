package ft.transform

import scala.math.{Pi, cos, sin}

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericFT {

  case class FTResult(resultList: List[Complex], additions: Int = 0, multiplications: Int = 0)

  protected def getW(arg: Double, dir: Int) =
    Complex(cos(2 * Pi * arg), dir * sin(2 * Pi * arg))

  def transform(list: List[Complex], dir: Boolean = false): List[Complex]

  def getMultiplicationsCount(N: Int): Int

  def getAdditionsCount(N: Int): Int
}
