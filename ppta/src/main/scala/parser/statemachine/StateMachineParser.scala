package parser.statemachine

import java.io.Reader

import lab1.Symbol
import lab2.{State, StateMachineParams, Transition}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object StateMachineParser extends RegexParsers with TransitionParser {

  val eol = sys.props("line.separator")

  val symbolsSeq: Regex = ".+".r

  val transitionSep = ";"
  val statesStart = "States:"
  val startStateStart = "Start state:"
  val endStatesStart = "End states:"
  val symbolsStart = "Input symbols:"
  val transitionStart = "Transitions:"

  override val whiteSpace: Regex = """[ \t]+""".r

  private def stateMachine: Parser[StateMachineParams] =
    (states <~ eol) ~
    (startState <~ eol) ~
    (endStates <~ eol) ~
    (symbols <~ eol) ~
    (transitions <~ eol) ^^ {

    case (((states ~ startState) ~ endStates) ~ symbols) ~ transitions =>
      StateMachineParams(states, startState, endStates, symbols, transitions)
  }

  private def states: Parser[Set[State]] = statesStart ~> symbolsSeq ^^ (_.toSet.map(State(_)))

  private def startState: Parser[State] = startStateStart ~> nonEmptySymbol ^^ (res => State(res.charAt(0)))

  private def endStates: Parser[Set[State]] = endStatesStart ~> symbolsSeq ^^ (_.toSet.map(State(_)))

  private def symbols: Parser[Set[Symbol]] = symbolsStart ~> symbolsSeq ^^ (_.toSet.map(Symbol(_)))

  private def transitions: Parser[Set[Transition]] =
    transitionStart ~> opt(eol) ~> transition ~ rep(transitionSep ~> opt(eol) ~> transition) <~ opt(transitionSep) ^^
    (res => res._1 :: res._2 toSet)

  def parse(reader: Reader): Option[StateMachineParams] = parseAll(stateMachine, reader) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => println(msg); None
  }
}
