package dsp.lab3

import dsp.lab3.transform.GenericWT
import dsp.util.Complex

import scala.math._
import scalax.chart.api._

/**
  * Created by vladkanash on 2/12/16.
  */

object WTCharts {

  private def func(x: Double): Double = 2 * cos(5 * x) + sin(x) - 4 * sin(7 * x)
  val N = 256

  private def wrapHz(func: Double => Double) = (e: Double) => func(e * 2 * Pi)

  private val generateTimeXs = for (i <- 0 until N) yield i * 1.0 / N
  private val generateFrequencyXs = for (i <- 0 until N) yield i

  private val generateTimeYs = generateTimeXs.map(wrapHz(func))
  private def generateFreqYs(res: List[Complex], phaseChart: Boolean = false) = phaseChart match {
    case false => res.map(_.magnitude)
    case true => res.map(_.angle)
  }

  private def getFTResult(transformation: GenericWT, revert: Boolean = false) = {
    val timeYs = generateTimeYs.toList
    transformation(timeYs, revert)
  }

  def getFrequencyChart(transformation: GenericWT) = {
    val res = getFTResult(transformation)
    val freqXs = generateFrequencyXs
    val freqYs = generateFreqYs(res)
    XYBarChart(freqXs zip freqYs, legend = false).toComponent
  }

  def getPhaseChart(transformation: GenericWT) = {
    val res = getFTResult(transformation)
    val freqXs = generateFrequencyXs
    val freqYs = generateFreqYs(res, phaseChart = true)
    XYBarChart(freqXs zip freqYs, legend = false).toComponent
  }

  def getTimeChart = {
    val XList = generateTimeXs.toList
    val YList = generateTimeYs.toList
    XYLineChart(XList zip YList, legend = false).toComponent
  }

  def getRevertChart(transformation: GenericWT) = {
    val timeXList = generateTimeXs.toList
    val result = getFTResult(transformation)
    val restored = transformation(result, dir = true)
    XYLineChart(timeXList zip restored.map(_.real), legend = false).toComponent
  }
}



