package lab2

import lab1.{Grammar, GrammarType, Rule, Symbol}

class FiniteStateMachine(val grammar: Grammar) {
  require(grammar.grammarType == GrammarType.Regular, "Type must be regular to build state machine")

  private val additionalNonTerminal: Char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" diff grammar.nonTerminals.mkString head

  private val extendedRules: Set[Rule] = grammar.rules ++ grammar.rules
    .filterNot(rule => hasExtendedRule(rule, grammar.rules))
    .filter(_.right.len == 1)
    .map(rule => Rule(rule.left, rule.right.contents + additionalNonTerminal))

  val states: Set[State] = grammar.nonTerminals + additionalNonTerminal

  val inputs: Set[Symbol] = grammar.terminals

  val startStates: Set[State] = Set(grammar.startSymbol)

  val transitions: Set[Transition] = extendedRules
    .map(r => Transition(r.left.contents.head, r.right.contents.head, r.right.contents.last))

  val endStates: Set[State] = extendedRules
    .filter(rule => rule.right.len == 2 && extendedRules.contains(Rule(rule.left, rule.right.contents.take(1))))
    .map(rule => rule.right.contents.last)

  def hasExtendedRule(tested: Rule, rules: Set[Rule]): Boolean =
    rules.exists(r =>
      r.left.contents == tested.left.contents &&
        r.right.contents.drop(1) == tested.right.contents)

  override val toString: String =
    s"""Q (States): ${states.mkString}
       |T (Input Symbols): ${inputs.mkString}
       |F (Transitions): ${transitions.mkString("\n\t", ";\n\t", "")}
       |H (Start states): ${startStates.mkString}
       |Z (End states): ${endStates.mkString}""".stripMargin
}