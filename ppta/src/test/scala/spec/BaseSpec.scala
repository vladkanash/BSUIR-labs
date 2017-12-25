package spec

import java.io.InputStreamReader

import org.scalatest.FlatSpec

import scala.io.Source

trait BaseSpec extends FlatSpec {

  protected def readInput(filepath: String): InputStreamReader =
    Source.fromResource(filepath).reader

}
