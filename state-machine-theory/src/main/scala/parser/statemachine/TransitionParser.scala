package parser.statemachine

import lab1.Symbol
import lab2.{State, Transition}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

trait TransitionParser extends RegexParsers {

  val arrow = "=>"
  val leftParenthesis = "("
  val rightParenthesis = ")"
  val nonEmptySymbol: Regex = ".{1}".r

  protected def transition: Parser[Transition] = (state ~ symbol <~ arrow) ~ state ^^ {
    case ((input ~ symbol) ~ output) => Transition(input, symbol, output)
  }

  private def symbol: Parser[Symbol] = leftParenthesis ~> nonEmptySymbol <~ rightParenthesis ^^ (res => Symbol(res.charAt(0)))
  private def state: Parser[State] = nonEmptySymbol ^^ (res => State(res.charAt(0)))

  def parse(str: CharSequence): Option[Transition] = parseAll(transition, str) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => print(msg); None
  }
}
