package ft

/**
  * Created by vladkanash on 2/16/16.
  */
object DFT extends GenericFT {

  override def transform(list: List[Complex], dir: Boolean = false): FTResult = {
    val N = list.length
    if (N == 1) return FTResult(list)

    val resultList = (0 until N).map({ k =>
      list.zipWithIndex.foldLeft(Complex(0, 0))({ (result, elem) =>
        val X = elem._1
        val m: Double = elem._2
        getW(m * k / N, if (dir) 1 else -1) * X + result
      })
    }).toList.map(e => if (dir) e / Complex(N, 0) else e)

    FTResult(resultList, N * (N - 1), N * N)
  }

}
