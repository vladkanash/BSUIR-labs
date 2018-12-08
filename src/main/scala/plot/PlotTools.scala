package plot

import org.jfree.data.xy.XYSeries

object PlotTools {

  private def xs(low: Double, up: Double, step: Double): List[Double] = {
    val range: Int = ((up - low) / step).toInt
    (0 until range).map(_ * step + low).toList
  }

  private def ys(low: Double, up: Double, step: Double, func: Double => Double): List[Double] =
    xs(low, up, step) map func

  def getDefaultSeries(low: Double, up: Double, step: Double, func: Double => Double): XYSeries = {
    val x = xs(low, up, step)
    val y = ys(low, up, step, func)
    val series = new XYSeries("Default")
    (x zip y).foreach(e => series.add(e._1, e._2))
    series
  }

  def getScatterSeries(low: Double, up: Double, step: Double, func: Double => Double): XYSeries = {
    val x = xs(low, up, step)
    val y = ys(low, up, step, func)
    val series = new XYSeries("Scatter")
    (x zip y).foreach(e => series.add(e._1, e._2))
    series
  }

}
