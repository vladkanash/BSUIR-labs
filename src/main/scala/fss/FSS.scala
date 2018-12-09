package fss

import fss.entity.{Config, Fish}
import scalax.chart.api.XYSeries

object FSS {

  def apply(cfg: Config, series: XYSeries = null): Iterable[Double] = {
    val school = (0 to cfg.popSize).map(_ => Fish(cfg.initPos(), 0, cfg.initWeight()))
    val result = optimize(school)(cfg, series)
    result.map(_.pos)
  }

  private def optimize(school: Iterable[Fish], iter: Int = 0)
                      (implicit cfg: Config, series: XYSeries): Iterable[Fish] = {

    def updatePlot(o: Iterable[Fish], n: Iterable[Fish]): Unit = {
      swing.Swing onEDT {
        if (iter > 0) o.foreach(f => series.remove(f.pos))
        n.foreach(f => series.add(f.pos, cfg.fitness(f.pos)))
      }
      Thread.sleep(100)
    }

    if (iter == cfg.iterations) {
      school
    } else {

      val tempSchool1 = move1(school)
      val tempSchool2 = move2(tempSchool1)
      val tempSchool3 = move3(school, tempSchool2)

      if (series != null) updatePlot(school, tempSchool3)
      optimize(tempSchool3, iter + 1)
    }
  }

  private def move1(school: Iterable[Fish])
                   (implicit cfg: Config): Iterable[Fish] = {
    def bestPos(oldPos: Double, newPos: Double) =
      if ((cfg.fitness(newPos) >= cfg.fitness(oldPos)) && cfg.inRange(newPos))
        newPos else oldPos

    school
      .map(f => Fish(f.pos, cfg.indVelocity(), f.weight))
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
        if (wNewSum > wOldSum) f.pos + cfg.initStep() * (f.pos - C)
        else f.pos - cfg.initStep() * (f.pos - C)

      Fish(adjustPos(newPos), f.velocity, f.weight)
    })
  }

  private def adjustPos(newPos: Double)(implicit cfg: Config): Double = {
    if (newPos >= cfg.up) cfg.up
    else if (newPos <= cfg.low) cfg.low
    else newPos
  }
}
