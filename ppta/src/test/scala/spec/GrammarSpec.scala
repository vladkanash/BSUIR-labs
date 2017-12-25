package spec

import lab1.Grammar
import org.scalatest.FlatSpec
import parser.grammar.GrammarParser

import scala.io.Source

trait GrammarSpec extends FlatSpec {

  private def readInput(filepath: String) =
    Source.fromResource(filepath).reader

  def parseFromFile(filepath: String): Option[Grammar] =
    GrammarParser.parse(readInput(filepath))

}
