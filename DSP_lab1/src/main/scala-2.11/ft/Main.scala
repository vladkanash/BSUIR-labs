package ft

import ft.util.double2complex

import scala.math._
import scalax.chart.api._

/**
  * Created by vladkanash on 2/12/16.
  */

object Main {

  def func(x: Double): Double = 2 * cos(5 * x) + sin(2 * x) + 1.5
  val N = 256

  def wrap_Hz(func: Double => Double) = (e: Double) => func(e * 2 * Pi)

  def generate_time_xs = for (i <- 0 until N) yield i * 1.0 / N
  def generate_freq_xs = for (i <- 0 until N/2) yield i

  def generate_time_ys = generate_time_xs.map(wrap_Hz(func))
  def generate_freq_ys(res: List[Complex]) =
    res.map(_.magnitude)
      .map(e => e * 2 / N) match {
      case h :: t => h / 2 :: t
      case Nil => Nil
    }

  def rotate(list: List[Double], i:Int): List[Double] = list.drop(i) ++ list.take(i)

  def main(args: Array[String]) {

//    generate_ys.foreach(e => printf("%.3f\n", e))
//    print("\n")

    val time_xs = generate_time_xs.toList
    val time_ys = generate_time_ys.toList

    val res = FFT.transform(time_ys.map(double2complex)).result
    val freq_xs = generate_freq_xs
    val freq_ys = generate_freq_ys(res)

    val res2 = FFT.transform(res, dir = true).result

    val freq_chart = XYLineChart(freq_xs zip freq_ys)
    val time_chart = XYLineChart(time_xs zip time_ys)
    val revert_chart = XYLineChart(time_xs zip res2.map(_.real))
    freq_chart.show("Frequency signal")
    time_chart.show("Time signal")
    revert_chart.show("Reverted signal")

//    res.foreach(e => printf("%.3f %.3f\n", e.real, e.imag))
  }
}


