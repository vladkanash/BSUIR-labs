package parser.grammar

import java.io.FileReader

import lab1.{Grammar, Rule, Symbol}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object GrammarParser extends RegexParsers with RuleParser {

  val ruleSep = ";"
  val startSymbolRegexp: Regex = "^[a-zA-Z]$".r
  val rulesStart = "Rules: "
  val terminalsStart = "Terminals: "
  val nonTerminalsStart = "NonTerminals: "
  val startSymbolStart = "StartSymbol: "
  val eol = sys.props("line.separator")

  override val whiteSpace: Regex = """[ \t]+""".r

  def grammar: Parser[Grammar] = (terminals <~ eol) ~ (nonTerminals <~ eol) ~ (rules <~ eol) ~ (startSymbol <~ eol) ^^ {
    case ((terms ~ nonTerms) ~ rls) ~ start => new Grammar(terms.toSet, nonTerms.toSet + start, rls.toSet, start)
  }

  def rules: Parser[List[Rule]] = rulesStart ~> rule ~ rep(ruleSep ~> rule) <~ opt(ruleSep) ^^
    (e => e._1 ::: e._2.flatten)

  def startSymbol: Parser[Symbol] = startSymbolStart ~> startSymbolRegexp ^^
    (res => Symbol(res.charAt(0)))

  def terminals: Parser[List[Symbol]] = terminalsStart ~> seq

  def nonTerminals: Parser[List[Symbol]] = nonTerminalsStart ~> seq

  def seq: Parser[List[Symbol]] = nonEmptyWord ^^ (_.toList.map(Symbol(_)))

  def parse(reader: FileReader): Option[Grammar] = parseAll(grammar, reader) match {
    case Success(result, _) => Some(result)
    case NoSuccess(msg, _) => println(msg); None
  }
}
