package fss.entity

case class Config(popSize: Int, wmax: Double, vmax: Double, iterations: Int, fitness: Double => Double)
