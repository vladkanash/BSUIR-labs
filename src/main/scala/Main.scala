import fss.FSS
import fss.entity.Config
import plot.PlotTools
import scalax.chart.api
import scalax.chart.api._

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val fitness: Double => Double =
      x => Math.sin(Math.PI * x + 2) * (0.9 * x + 1.1)

    val cfg: Config = Config(
      popSize = args(0).toInt,
      wmax = args(1).toDouble,
      vmax = args(2).toDouble,
      smax = args(3).toDouble,
      iterations = args(4).toInt,
      low = args(5).toDouble,
      up = args(6).toDouble,
      fitness = fitness)

    implicit val scatterSeries: api.XYSeries = new XYSeries("second")
    val defaultseries = PlotTools.getDefaultSeries(cfg.low, cfg.up, 0.1, fitness)
    val collection: XYSeriesCollection = new XYSeriesCollection(defaultseries)
    collection.addSeries(scatterSeries)

    val chart = XYLineChart(collection)

    val renderer = new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer()
    renderer.setSeriesLinesVisible(0, true)
    renderer.setSeriesShapesVisible(0, false)

    renderer.setSeriesLinesVisible(1, false)
    renderer.setSeriesShapesVisible(1, true)

    chart.plot.setRenderer(renderer)
    chart.show()

    val plot1 = (1 until 100).map(e => {
      val conf = Config(e, wmax = 4, vmax = 2, smax = 4, 30, cfg.low, cfg.up, cfg.fitness)
      FSS(conf).map(conf.fitness).max

    }).zipWithIndex.map(_.swap)

    val plotChart = XYLineChart(plot1)
    plotChart.show()

    FSS(cfg, scatterSeries)
  }
}
