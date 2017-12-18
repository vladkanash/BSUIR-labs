package parser.statemachine

import java.io.FileReader

import lab1.Symbol
import lab2.{State, StateMachineParams, Transition}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object StateMachineParser extends RegexParsers with TransitionParser {

  val eol = sys.props("line.separator")

  val startStateRegexp: Regex = "[A-Z]{1}".r
  val symbolsRegexp: Regex = "[~&a-z]+".r
  val stateSeq: Regex = "[A-Z]+".r

  val transitionSep = ";"
  val statesStart = "States:"
  val startStateStart = "Start state:"
  val endStatesStart = "End states:"
  val symbolsStart = "Input symbols:"
  val transitionStart = "Transitions:"

  override val whiteSpace: Regex = """[ \t]+""".r

  def stateMachine: Parser[StateMachineParams] =
    (states <~ eol) ~
    (startState <~ eol) ~
    (endStates <~ eol) ~
    (symbols <~ eol) ~
    (transitions <~ eol) ^^ {

    case (((states ~ startState) ~ endStates) ~ symbols) ~ transitions =>
      StateMachineParams(states, startState, endStates, symbols, transitions)
  }

  def states: Parser[Set[State]] = statesStart ~> stateSeq ^^ (_.toSet.map(State(_)))

  def startState: Parser[State] = startStateStart ~> startStateRegexp ^^ (res => State(res.charAt(0)))

  def endStates: Parser[Set[State]] = endStatesStart ~> stateSeq ^^ (_.toSet.map(State(_)))

  def symbols: Parser[Set[Symbol]] = symbolsStart ~> symbolsRegexp ^^ (_.toSet.map(Symbol(_)))

  def transitions: Parser[Set[Transition]] =
    transitionStart ~> opt(eol) ~> transition ~ rep(transitionSep ~> opt(eol) ~> transition) <~ opt(transitionSep) ^^
    (res => res._1 :: res._2 toSet)

  def parse(reader: FileReader): Option[StateMachineParams] = parseAll(stateMachine, reader) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => println(msg); None
  }
}
