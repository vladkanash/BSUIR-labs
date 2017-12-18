package lab2

import lab1.{EmptyWord, Grammar, GrammarType, Rule, Symbol}

import scala.language.postfixOps

class GrammarFiniteStateMachine(val grammar: Grammar) extends FiniteStateMachine(GrammarFiniteStateMachine(grammar))

object GrammarFiniteStateMachine {

  def apply(grammar: Grammar): StateMachineParams = {
    require(grammar.grammarType == GrammarType.Regular, "Type must be regular to build state machine")

    def hasExtendedRule(tested: Rule, rules: Set[Rule]): Boolean =
      rules.exists(r =>
        r.left.contents == tested.left.contents &&
        r.right.contents.drop(1) == tested.right.contents)

    val stateMachineNewStateNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" diff grammar.nonTerminals.mkString

    val additionalNonTerminal: Char = stateMachineNewStateNames head

    val extendedRules: Set[Rule] = grammar.rules ++ grammar.rules
      .filterNot(rule => hasExtendedRule(rule, grammar.rules))
      .filter(_.right.len == 1)
      .map(rule => Rule(rule.left, rule.right.contents + additionalNonTerminal))

    val additionalEndState: Set[Symbol] =
      if (grammar.rules.contains(Rule(Set(grammar.startSymbol), EmptyWord)))
        Set(grammar.startSymbol)
      else
        Set.empty

    val states: Set[State] =
      if (extendedRules.size > grammar.rules.size)
        grammar.nonTerminals + additionalNonTerminal
      else
        grammar.nonTerminals

    val inputs: Set[Symbol] = grammar.terminals

    val startState: State = grammar.startSymbol

    val transitions: Set[Transition] = extendedRules
      .filter(_.right.len == 2)
      .map(r => Transition(r.left.contents.head, r.right.contents.head, r.right.contents.last))

    val endStates: Set[State] = extendedRules
      .filter(rule =>
        rule.right.len == 2 &&
        extendedRules.contains(Rule(rule.left, rule.right.contents.take(1))))
      .map(rule => rule.right.contents.last) ++ additionalEndState

    StateMachineParams(states, startState, endStates, inputs, transitions)
  }
}