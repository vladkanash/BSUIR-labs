package parser.statemachine


import lab2.Transition
import org.scalatest.FlatSpec

class TransitionParserTest extends FlatSpec {

  val parser: TransitionParser = new TransitionParser {}

  "A transition parser" should "recognize a correct input string" in {
    val input = "D(a) => C"
    val result = parser.parse(input)

    assert(result.nonEmpty)
  }

  it should "not recognize an incorrect string" in {
    val input = "D(aaa) => C"
    val result = parser.parse(input)

    assert(result.isEmpty)
  }

  it should "process white symbols" in {
    val input = "M   (g)    =>  \n \t\tg "
    val result = parser.parse(input)

    assert(result.nonEmpty)
    assert(result.get == new Transition("Mgg"))
  }
}
