package parser.grammar

import java.io.Reader

import lab1.{Grammar, Rule, Symbol}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object GrammarParser extends RegexParsers with RuleParser {

  val ruleSep = ";"
  val startSymbolRegexp: Regex = ".{1}".r
  val rulesStart = "Rules: "
  val terminalsStart = "Terminals: "
  val nonTerminalsStart = "NonTerminals: "
  val startSymbolStart = "StartSymbol: "
  val eol = sys.props("line.separator")

  override val whiteSpace: Regex = """[ \t]+""".r

  private def grammar: Parser[Grammar] =
    (terminals <~ eol) ~
    (nonTerminals <~ eol) ~
    (startSymbol <~ eol) ~
    (rules <~ eol)  ^^ {
    case ((terms ~ nonTerms) ~ start) ~ rls => new Grammar(terms.toSet, nonTerms.toSet + start, rls.toSet, start)
  }

  private def rules: Parser[List[Rule]] = rulesStart ~> opt(eol) ~> rule ~ rep(ruleSep ~> opt(eol) ~> rule) <~ opt(ruleSep) ^^ {
    case rule ~ rules => rule ::: rules.flatten
  }

  private def startSymbol: Parser[Symbol] = startSymbolStart ~> startSymbolRegexp ^^
    (res => Symbol(res.charAt(0)))

  private def terminals: Parser[List[Symbol]] = terminalsStart ~> seq

  private def nonTerminals: Parser[List[Symbol]] = nonTerminalsStart ~> seq

  private def seq: Parser[List[Symbol]] = nonEmptyWord ^^ (_.toList.map(Symbol(_)))

  def parse(reader: Reader): Option[Grammar] = parseAll(grammar, reader) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => println(msg); None
  }
}
