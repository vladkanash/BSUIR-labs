package parser.statemachine

import java.io.FileReader

import lab1.Symbol
import lab2.{State, StateMachineParams, Transition}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object StateMachineParser extends RegexParsers with TransitionParser {

  val ruleSep = ";"
  val startSymbolRegexp: Regex = "^[a-zA-Z]$".r
  val rulesStart = "Rules: "
  val terminalsStart = "Terminals: "
  val nonTerminalsStart = "NonTerminals: "
  val startSymbolStart = "StartSymbol: "
  val eol = sys.props("line.separator")

  override val whiteSpace: Regex = """[ \t]+""".r

  def stateMachine: Parser[StateMachineParams] = (states <~ eol) ~ (startState <~ eol) ~ (endStates <~ eol) ~ (symbols <~ eol) ~ (transitions <~ eol) ^^ {
    case (((states ~ startState) ~ endStates) ~ symbols) ~ transitions => StateMachineParams(states, startState, endStates, symbols, transitions)
  }

  def states: Parser[Set[State]] = ???
  def startState: Parser[State] = ???
  def endStates: Parser[Set[State]] = ???
  def symbols: Parser[Set[Symbol]] = ???
  def transitions: Parser[Set[Transition]] = ???

  def parse(reader: FileReader): Option[StateMachineParams] = parseAll(stateMachine, reader) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => println(msg); None
  }
}
