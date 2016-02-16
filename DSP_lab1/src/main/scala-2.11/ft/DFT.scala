package ft

import scala.math.{Pi, cos, sin}

/**
  * Created by vladkanash on 2/16/16.
  */
object DFT extends GenericFT {

  private def getDiscreteW(N: Int, dir: Int, args: (Int, Int)): Complex = {
    val arg = args._1 * args._2 * 2 * Pi / N
    Complex(cos(arg), dir * sin(arg))
  }

  def transform(list: List[Complex], dir: Boolean = false): FTResult = {
    val N = list.length
    if (N == 1) return FTResult(list)

    val result = (0 until N).map({ k =>
      list.zipWithIndex.foldLeft(Complex(0, 0))({ (result, elem) =>
        val X = elem._1
        val m = elem._2
        getDiscreteW(N, if (dir) 1 else -1, (m, k)) * X + result
      })
    }).toList.map(e => if (dir) e / Complex(N, 0) else e)

    FTResult(result)
  }

}
