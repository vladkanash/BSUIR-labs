import fss.entity.{Config, Fish}
import plot.PlotTools
import scalax.chart.api
import scalax.chart.api._

import scala.util.Random

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

    Thread.sleep(1500)

    val plot1 = (1 until 200).map(e => {
      val conf = Config(30, cfg.wmax, cfg.vmax, cfg.smax, e, cfg.low, cfg.up, cfg.fitness)
      val res = FSS(conf, scatterSeries, anim = false)
      val avg = res.map(_.pos).sum
      val fit = avg / res.size
      fit
    }).zipWithIndex.map(_.swap)

    val plotChart = XYLineChart(plot1)
    plotChart.show()

//    FSS(cfg, scatterSeries, anim = true)
  }

  def FSS(config: Config, series: XYSeries, anim: Boolean = false): Iterable[Fish] = {
    implicit val cfg: Config = config
    implicit val srs: XYSeries = series

    val school = (0 to config.popSize).map(_ => Fish(initPos(), 0, initWeight(config.wmax)))

    optimize(school, animation = anim)
  }

  def optimize(school: Iterable[Fish], iter: Int = 0, animation: Boolean = false)
              (implicit cfg: Config, series: XYSeries): Iterable[Fish] = {

    def updatePlot(o: Iterable[Fish], n: Iterable[Fish]): Unit = {
      swing.Swing onEDT {
        if (iter > 0) o.foreach(f => series.remove(f.pos))
        n.foreach(f => series.add(f.pos, cfg.fitness(f.pos)))
      }
      Thread.sleep(300)
    }

    if (iter == cfg.iterations) {
      school
    } else {

      val tempSchool1 = move1(school)
      val tempSchool2 = move2(tempSchool1)
      val tempSchool3 = move3(school, tempSchool2)

      if (animation) updatePlot(school, tempSchool3)
      optimize(tempSchool3, iter + 1, animation)
    }
  }

  private def move1(school: Iterable[Fish])(implicit cfg: Config): Iterable[Fish] = {
    def bestPos(oldPos: Double, newPos: Double) =
      if ((cfg.fitness(newPos) >= cfg.fitness(oldPos)) && inRange(newPos))
        newPos else oldPos

    school
      .map(f => Fish(f.pos, indVelocity(cfg.vmax), f.weight))
      .map(f => Fish(bestPos(f.pos, f.pos + f.velocity), f.velocity, f.weight))
  }

  private def move2(school: Iterable[Fish])
                   (implicit cfg: Config): Iterable[Fish] = {

    def initM(oldSchool: Iterable[Fish], newSchool: Iterable[Fish]): Double = {
      val top = (oldSchool zip newSchool).map(e => e._2.velocity * (cfg.fitness(e._2.pos) - cfg.fitness(e._1.pos))).sum
      val bot = (oldSchool zip newSchool).map(e => cfg.fitness(e._2.pos) - cfg.fitness(e._1.pos)).sum
      if (bot == 0) 0 else top / bot
    }

    val m = initM(school, school)
    school.map(f => Fish(adjustPos(f.pos + m), f.velocity, f.weight))
  }

  private def move3(oldSchool: Iterable[Fish], newSchool: Iterable[Fish])
                   (implicit cfg: Config): Iterable[Fish] = {
    val weights = (oldSchool zip newSchool)
      .map(f => {
        val newFit: Double = cfg.fitness(f._2.pos)
        val oldFit: Double = cfg.fitness(f._1.pos)
        val maxFit: Double = oldFit max newFit

        val W =
          if (maxFit != 0) f._2.weight + (newFit - oldFit) / maxFit
          else 0

        val resW =
          if (W < 1) 1
          else if (W > cfg.wmax) cfg.wmax
          else W

        Fish(f._2.pos, f._2.velocity, resW)
      })

    val (cTop, cBot) = weights.map(f => (f.weight * f.pos, f.weight)) unzip
    val C = cTop.sum / cBot.sum

    val wOldSum = oldSchool.map(_.weight).sum
    val wNewSum = newSchool.map(_.weight).sum

    weights.map(f => {
      val newPos =
        if (wNewSum > wOldSum) f.pos + initStep(cfg.smax) * (f.pos - C)
        else f.pos - initStep(cfg.smax) * (f.pos - C)

      Fish(adjustPos(newPos), f.velocity, f.weight)
    })
  }

  private def adjustPos(newPos: Double)(implicit cfg: Config): Double = {
    if (newPos >= cfg.up) cfg.up
    else if (newPos <= cfg.low) cfg.low
    else newPos
  }

  def inRange(pos: Double)(implicit cfg: Config): Boolean = (pos <= cfg.up) && (pos >= cfg.low)
  def indVelocity(vmax: Double): Double = (Random.nextDouble() - 0.5) * vmax
  def initPos()(implicit cfg: Config): Double = (Random.nextDouble() * cfg.up) + cfg.low
  def initWeight(wmax: Double): Double = wmax / 2
  def initStep(smax: Double): Double = Random.nextDouble() * smax

}
