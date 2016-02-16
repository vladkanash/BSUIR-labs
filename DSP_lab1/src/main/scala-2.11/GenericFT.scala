import scala.math.{sin, cos, Pi}

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericFT {

  protected def get_w(n: Int, dir: Int) =
    Complex(cos(2 * Pi / n), dir * sin(2 * Pi / n))

  def transform(list: List[Complex], dir: Boolean = false): List[Complex]

}
