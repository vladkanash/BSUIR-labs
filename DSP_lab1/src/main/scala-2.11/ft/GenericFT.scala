package ft



import scala.math.{Pi, cos, sin}

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericFT {

  case class FTResult(result: List[Complex], additions: Int = 0, multiplications: Int = 0)

  def transform(list: List[Complex], dir: Boolean = false): FTResult

}
