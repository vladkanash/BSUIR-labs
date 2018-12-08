package fss.entity

case class Config(popSize: Int,
                  wmax: Double,
                  vmax: Double,
                  smax: Double,
                  iterations: Int,
                  low: Double,
                  up: Double,
                  fitness: Double => Double)
