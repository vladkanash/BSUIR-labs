package ft.transform

/**
  * Created by vladkanash on 2/16/16.
  */
object DFT extends GenericFT {

  override def transform(list: List[Complex], dir: Boolean = false): List[Complex] = {
    val N = list.length
    (0 until N).map({ k =>
      list.zipWithIndex.foldLeft(Complex(0))({ (result, elem) =>
        val X = elem._1
        val m: Double = elem._2
        getW(m * k / N, if (dir) 1 else -1) * X + result
      })
    }).toList.map(e => if (dir) e / N else e)
  }

  override def getMultiplicationsCount(N: Int): Int = N * N

  override def getAdditionsCount(N: Int): Int = N * (N - 1)
}
