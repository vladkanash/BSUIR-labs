package parser.grammar

import org.scalatest.FlatSpec
import util.GrammarUtils

class GrammarParserTest extends FlatSpec with GrammarUtils {

  "A grammar parser" should "parse correct grammar from file" in {
    val result = parseFromFile("grammar/testGrammar1.txt")

    assert(result.nonEmpty)
    assert(result.get.nonTerminals.size == 8)
  }

  it should "Parse mode complex grammar" in {
    val result = parseFromFile("grammar/testGrammar2.txt")

    assert(result.nonEmpty)
    assert(result.get.rules.size == 10)
  }

  it should "not recognize grammar with invalid rule syntax" in {
    val result = parseFromFile("grammar/testGrammar3.txt")

    assert(result.isEmpty)
  }

  it should "not recognize grammar with more than one start symbol" in {
    val result = parseFromFile("grammar/testGrammar4.txt")

    assert(result.isEmpty)
  }
}
