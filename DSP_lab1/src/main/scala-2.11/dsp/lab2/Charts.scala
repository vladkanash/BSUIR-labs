package dsp.lab2

import dsp.lab2.transform.{DiscreteCorrelation, DiscreteConvolution, Correlation, Convolution}
import dsp.util.RotatedIterator

import scalax.chart.module.ChartFactories.{XYBarChart, XYLineChart}
import scala.math._

/**
  * Created by vladkanash on 3/14/16.
  */
object Charts {

  private val N = 16

  private def func1(x: Double) = sin(x)
  private def func2(x: Double) = cos(x)

  private val xs = (0 until N).toList

  private val signal1: List[Double] = List(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0)
  private val signal2: List[Double] = List(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0)

//  private val signal1: List[Double] = (0 until N).map(e => func1(e)).toList
//  private val signal2: List[Double] = (0 until N).map(e => func2(e)).toList

  private def getLineChart(ys: List[Double]) = XYLineChart(xs zip ys, legend = false).toComponent

  def getSignal1Chart = getLineChart(signal1)
  def getSignal2Chart = getLineChart(signal2)
  def getCorrelationChart = getLineChart(new RotatedIterator(Correlation(signal1, signal2), N/2).toList)
  def getConvolutionChart = getLineChart(new RotatedIterator(Convolution(signal1, signal2), N/2).toList)
  def getDiscreteConvolutionChart =
    getLineChart(new RotatedIterator(DiscreteConvolution(signal1, signal2), N/2).toList)
  def getDiscreteCorrelationChart =
    getLineChart(new RotatedIterator(DiscreteCorrelation(signal1, signal2), N/2).toList)

}
