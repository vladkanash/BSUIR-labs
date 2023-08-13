package sbc

import sbc.entity.Config
import scalax.chart.api
import scalax.chart.api._

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val fitness: (Double, Double) => Double =
      (x, y) => -(x * x + y * y) + x

    val cfg: Config = Config(
      iterations = args(0).toInt,
      bestPoints = args(1).toInt,
      selectedPoints = args(2).toInt,
      scoutBeesCount = args(3).toInt,
      bestBeesCount = args(4).toInt,
      selectedBeesCount = args(5).toInt,
      localitySize = args(6).toDouble,
      low = args(7).toDouble,
      up = args(8).toDouble,
      fitness = fitness
    )

    val scoutSeries: api.XYSeries = new XYSeries("scouts")
    val bestSeries: api.XYSeries = new XYSeries("best")
    val selectedSeries: api.XYSeries = new XYSeries("selected")

//    val defaultseries = PlotTools.getDefaultSeries(cfg.low, cfg.up, 0.1, fitness)
    val collection: XYSeriesCollection = new XYSeriesCollection(scoutSeries)
    collection.addSeries(bestSeries)
    collection.addSeries(selectedSeries)

    val chart = XYLineChart(collection)

    val renderer = new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer()

    renderer.setSeriesLinesVisible(0, false)
    renderer.setSeriesShapesVisible(0, true)

    renderer.setSeriesLinesVisible(1, false)
    renderer.setSeriesShapesVisible(1, true)

    renderer.setSeriesLinesVisible(2, false)
    renderer.setSeriesShapesVisible(2, true)

    chart.plot.setRenderer(renderer)
    chart.show()

    val plot1 = (1 until 100).map(e => {
      val conf = Config(e,
        cfg.bestPoints,
        cfg.selectedPoints,
        cfg.scoutBeesCount,
        cfg.bestBeesCount,
        cfg.selectedBeesCount,
        cfg.localitySize,
        cfg.low,
        cfg.up,
        cfg.fitness)

      val res = SBC(conf)
      cfg.fitness(res._1, res._2)

    }).zipWithIndex.map(_.swap)

    val plotChart = XYLineChart(plot1)
    plotChart.show()

    SBC(cfg, bestSeries, selectedSeries, scoutSeries)
  }
}
