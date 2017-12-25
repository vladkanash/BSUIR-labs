package parser.grammar

import lab1.Rule
import org.scalatest.FlatSpec

class RuleParserTest extends FlatSpec {

  val parser: RuleParser = new RuleParser {}

  "A rule parser" should "recognize a correct rule string" in {
    val input = "AB => E"
    val result = parser.parse(input)

    assert(result.nonEmpty)
    assert(result.head == Rule("AB", "E"))
  }

  it should "recognize composed rules" in {
    val input = "CD => a|bc|deg"
    val result = parser.parse(input)

    assert(result.lengthCompare(3) == 0)
    assert(result.last == Rule("CD", "deg"))
  }

  it should "process white symbols" in {
    val input = "MN    =>  \n  k|\t\tl|      gggdd    "
    val result = parser.parse(input)

    assert(result.nonEmpty)
    assert(result.head == Rule("MN", "k"))
  }

  it should "return empty list when input is invalid" in {
    val input = "CD => b|c|"
    val result = parser.parse(input)

    assert(result.isEmpty)
  }

  it should "successfully recognize a list of rules" in {
    val input = List("m => bv| vc", "A => ll|lll", "vc => a", "O=>U|VC|CV|VV|CC")
    val result = parser.parse(input)

    assert(result.nonEmpty)
    assert(result.lengthCompare(10) == 0)
  }
}
