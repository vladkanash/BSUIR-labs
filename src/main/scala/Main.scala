import fss.entity.{Config, Fish}

import scala.util.Random

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val fitness: Double => Double =
      x => (1.5 * x + 0.9) * Math.sin(Math.PI * x + 1.1)

    val config = Config(
      popSize = args(0).toInt,
      wmax = args(1).toDouble,
      vmax = args(2).toDouble,
      iterations = args(3).toInt,
      fitness = fitness)


    optimize(config)
  }

  def optimize(config: Config): Double = {
    val school = (0 to config.popSize)
      .map(_ => Fish(initPos(), 0, initWeight(config.wmax)))

    val result = round(school, config)
    1
  }

  def round(school: Iterable[Fish], config: Config, iter: Int = 0): Iterable[Fish] = {
    if (iter == config.iterations) {
      school
    } else {
      val tempSchool = individualMove(school, config)

      val m = initM(school, tempSchool, config)

      val tempSchool2 = tempSchool.map(f => Fish(f.pos + m, f.velocity, f.weight))
      round(tempSchool2, config, iter + 1)
    }
  }

  private def initM(oldSchool: Iterable[Fish],
                    newSchool: Iterable[Fish], cfg: Config): Double = {
    (oldSchool zip newSchool).map(e => e._2.velocity * (cfg.fitness(e._2.pos) - cfg.fitness(e._1.pos))).sum /
    (oldSchool zip newSchool).map(e => cfg.fitness(e._2.pos) - cfg.fitness(e._1.pos)).sum
  }

  private def individualMove(school: Iterable[Fish], cfg: Config): Iterable[Fish] = {
    def bestPos(oldPos: Double, newPos: Double) =
      if (cfg.fitness(newPos) >= cfg.fitness(oldPos)) newPos else oldPos

    school
      .map(f => Fish(f.pos, indVelocity(cfg.vmax), f.weight))
      .map(f => Fish(bestPos(f.pos, f.pos + f.velocity), f.velocity, f.weight))
  }



  def indVelocity(vmax: Double): Double = Random.nextDouble() * vmax
  def initPos(): Double = Random.nextDouble()
  def initWeight(wmax: Double): Double = wmax / 2


}
