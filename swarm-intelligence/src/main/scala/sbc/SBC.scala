package sbc

import sbc.entity.{Bee, Config}
import scalax.chart.api.XYSeries

object SBC {

  def apply(config: Config, bestSeries: XYSeries = null, selectedSeries: XYSeries = null, scoutSeries: XYSeries = null): (Double, Double) = {
    val initValues = (0 until config.allBeesCount)
      .map(_ => Bee(config.getRandomValue, config.getRandomValue))
      .sortBy(b => config.fitness(b.pos._1, b.pos._2)).reverse

    val result = optimize(0, initValues, config.localitySize)(config, bestSeries, selectedSeries, scoutSeries)
    result.head
  }

  private def optimize(iter: Int, bees: Iterable[Bee], localitySize: Double)
                      (implicit config: Config,
                       bestSeries: XYSeries,
                       selectedSeries: XYSeries,
                       scoutSeries: XYSeries): Iterable[(Double, Double)] = {

    def updatePlot(nb: Iterable[Bee], ns: Iterable[Bee], nsc: Iterable[Bee]): Unit = {
      swing.Swing onEDT {

        bestSeries.clear()
        selectedSeries.clear()
        scoutSeries.clear()

        nb.foreach(f => bestSeries.add(f.pos._1, f.pos._2))
        ns.foreach(f => selectedSeries.add(f.pos._1, f.pos._2))
        nsc.foreach(f => scoutSeries.add(f.pos._1, f.pos._2))
      }
      Thread.sleep(800)
    }

    def sendBees(sites: Iterable[Bee], beesCount: Int) =
      sites.flatMap(bee =>
        (0 until beesCount)
          .map(_ => Bee(config.getLocalValue(bee.pos._1, localitySize), config.getLocalValue(bee.pos._2, localitySize)))
      )

    if (iter == config.iterations) {
      bees.map(_.pos)
    } else {

      val best = bees.take(config.bestPoints)
      val selected = bees.slice(config.bestPoints, config.bestPoints + config.selectedPoints)

      val bestResults = sendBees(best, config.bestBeesCount)
      val selectedResults = sendBees(selected, config.selectedBeesCount)
      val scoutResults = (0 until config.scoutBeesCount).map(_ => Bee(config.getRandomValue, config.getRandomValue))

      val newPoints =
        (bestResults ++ selectedResults ++ scoutResults)
          .toList.sortBy(b => config.fitness(b.pos._1, b.pos._2)).reverse

      if (bestSeries != null) updatePlot(bestResults, selectedResults, scoutResults)

      optimize(iter + 1, newPoints, localitySize * 0.96)(config, bestSeries, selectedSeries, scoutSeries)
    }
  }
}
