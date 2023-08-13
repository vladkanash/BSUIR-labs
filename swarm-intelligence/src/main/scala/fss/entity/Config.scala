package fss.entity

import scala.util.Random

case class Config(popSize: Int,
                  wmax: Double,
                  vmax: Double,
                  smax: Double,
                  iterations: Int,
                  low: Double,
                  up: Double,
                  fitness: Double => Double) {

  def inRange(pos: Double): Boolean = (pos <= up) && (pos >= low)

  def indVelocity(): Double = (Random.nextDouble() - 0.5) * vmax

  def initPos(): Double = (Random.nextDouble() * up) + low

  def initWeight(): Double = wmax / 2

  def initStep(): Double = Random.nextDouble() * smax
}
