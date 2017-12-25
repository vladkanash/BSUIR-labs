package util

import lab1.Grammar
import parser.grammar.GrammarParser

import scala.io.Source

trait GrammarUtils {

  private def readInput(filepath: String) =
    Source.fromResource(filepath).reader

  def parseFromFile(filepath: String): Option[Grammar] =
    GrammarParser.parse(readInput(filepath))

}
