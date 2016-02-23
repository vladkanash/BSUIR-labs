package ft

import ft.transform.{FFT, DFT, GenericFT}
import ft.util.double2complex

import scala.math._
import scalax.chart.api._

/**
  * Created by vladkanash on 2/12/16.
  */

object FTCharts {

  def func(x: Double): Double = 2 * cos(5 * x) + sin(2 * x) + 1.5
  val N = 64

  private def wrapHz(func: Double => Double) = (e: Double) => func(e * 2 * Pi)

  private def generateTimeXs = for (i <- 0 until N) yield i * 1.0 / N
  private def generateFrequencyXs = for (i <- 0 until N/2) yield i

  private def generateTimeYs = generateTimeXs.map(wrapHz(func))
  private def generateFreqYs(res: List[Complex], f: Complex => Double) =
    res.map(f)
      .map(e => e * 2 / N) match {
      case h :: t => h / 2 :: t
      case Nil => Nil
    }

  private def getFTresult(transformation: GenericFT) = {
    val timeYs = generateTimeYs.toList
    transformation.transform(timeYs.map(double2complex))
  }

  def getDFTMultiplications = getFTresult(DFT).multiplications

  def getDFTAdditions = getFTresult(DFT).additions

  def getFFTMultiplications = getFTresult(FFT).multiplications

  def getFFTAdditions = getFTresult(FFT).additions


  def getFrequencyChart(transformation: GenericFT) = {
    val timeYs = generateTimeYs.toList
    val res = transformation.transform(timeYs.map(double2complex)).resultList
    val freqXs = generateFrequencyXs
    val freqYs = generateFreqYs(res, _.magnitude)

    XYLineChart(freqXs zip freqYs).toComponent
  }

  def getPhaseChart(transformation: GenericFT) = {
    val timeYs = generateTimeYs.toList
    val res = transformation.transform(timeYs.map(double2complex)).resultList
    val freqXs = generateFrequencyXs
    val freqYs = generateFreqYs(res, _.angle)

    XYLineChart(freqXs zip freqYs).toComponent
  }

  def getTimeChart = {
    val XList = generateTimeXs.toList
    val YList = generateTimeYs.toList
    XYLineChart(XList zip YList).toComponent
  }

  def getRevertChart(transformation: GenericFT) = {
    val timeXList = generateTimeXs.toList
    val timeYList = generateTimeYs.toList
    val result = transformation.transform(timeYList.map(double2complex)).resultList
    val result2 = transformation.transform(result, dir = true).resultList
    XYLineChart(timeXList zip result2.map(_.real)).toComponent
  }
}


