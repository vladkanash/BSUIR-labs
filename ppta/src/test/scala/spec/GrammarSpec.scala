package spec

import lab1.Grammar
import parser.grammar.GrammarParser

trait GrammarSpec extends BaseSpec {

  def parseFromFile(filepath: String): Option[Grammar] =
    GrammarParser.parse(readInput(filepath))

}
