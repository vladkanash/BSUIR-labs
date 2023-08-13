package sbc.entity

import scala.util.Random

case class Config(iterations: Int,
                  bestPoints: Int,
                  selectedPoints: Int,
                  scoutBeesCount: Int,
                  bestBeesCount: Int,
                  selectedBeesCount: Int,
                  localitySize: Double,
                  low: Double,
                  up: Double,
                  fitness: (Double, Double) => Double) {

  val allBeesCount: Int = scoutBeesCount + selectedPoints * selectedBeesCount + bestPoints * bestBeesCount

  def getRandomValue: Double = Random.nextDouble() * (up - low) + low

  def getLocalValue(value: Double, localitySize: Double = this.localitySize): Double = {
    val res = Random.nextDouble() * (localitySize + localitySize) + (value - localitySize)
    if (res > up) up
    else if (res < low) low
    else res
  }

}