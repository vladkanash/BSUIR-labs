package dsp.lab4

import dsp.lab4.transform.{FWLT, GenericWLT}
import dsp.util.Complex

import scala.math._
import scalax.chart.api._

/**
  * Created by vladkanash on 2/12/16.
  */

object WTCharts {

  private def func(x: Double): Double = 2 * cos(5 * x)
  val N = 16

  private def wrapHz(func: Double => Double) = (e: Double) => func(e * 2 * Pi)

  private val generateTimeXs = for (i <- 0 until N) yield i * 1.0 / N
  private val generateFrequencyXs = for (i <- 0 until N/2) yield i

  private val generateTimeYs = List(6, 1, 13, 4, 8, 11, 13, 12, 10, 6, 2, 7, 12, 10, 6, 8)
  private def generateFreqYs(res: List[Complex], phaseChart: Boolean = false) = phaseChart match {
    case false => res.map(_.magnitude)
      .map(e => e * 2 / N) match {
      case h :: t => h / 2 :: t
      case Nil => Nil
    }
    case true => res.map(_.angle)
  }

  private def getWTResult(transformation: GenericWLT, depth: Int = 4, revert: Boolean = false) = {
    val timeYs = generateTimeYs.toList
    transformation(timeYs, depth, revert)
  }

  private def getHighs(depth: Int) = {
    val list = generateTimeYs.toList
    FWLT.getHighs(list, depth)
  }

  def getTimeChart = {
    val XList = generateTimeXs.toList
    val YList = generateTimeYs.toList
    XYLineChart(XList zip YList, legend = false).toComponent
  }

  def getWaveletChart(depth: Int) = {
    val res = getWTResult(FWLT, depth)
    val freqXs = generateFrequencyXs
    val freqYs = generateFreqYs(res)
    XYLineChart(freqXs zip freqYs, legend = false).toComponent
  }

  def getRevertChart(depth: Int) = {
    val timeXList = generateTimeXs.toList
    val result = getWTResult(FWLT, depth)
    val highs = List(getHighs(4),
      getHighs(3),
      getHighs(2),
      getHighs(1))
    val restored = FWLT(result, depth, dir = true, highs)
    XYLineChart(timeXList zip restored.map(_.real), legend = false).toComponent
  }
}


