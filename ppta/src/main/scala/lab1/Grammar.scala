package lab1

class Grammar(val terminals: Set[Symbol],
              val nonTerminals: Set[Symbol],
              val rules: Set[Rule],
              val startSymbol: Symbol) {

  require((terminals & nonTerminals).isEmpty,
    "Duplicate symbols found in terminals and non terminals")
  require(nonTerminals.contains(startSymbol),
    "Start symbol should be one of the nonTerminals")
  require(rules forall (rule => rule.left.allIn(terminals ++ nonTerminals)),
    "Incorrect symbol found in rule left part")
  require(rules forall (rule => rule.right.allIn(terminals ++ nonTerminals)),
    "Incorrect symbol found in rule right part")

  lazy val grammarType: GrammarType.Value =
         if (isRegular) GrammarType.Regular
    else if (isContextFree) GrammarType.ContextFree
    else if (isContextDependent) GrammarType.ContextDependent
    else GrammarType.Type0

  private lazy val isContextFree = rules forall (rule =>
    rule.left.len == 1 &&
    rule.left.allIn(nonTerminals))

  private lazy val isContextDependent = rules forall (rule =>
    rule.right.len >= rule.left.len &&
    rule.left.allIn(terminals ++ nonTerminals) &&
    rule.right.allIn(terminals ++ nonTerminals) &&
    rule.right != EmptyWord)

  private lazy val isRegular = isContextFree && (rules forall (rule =>
    (rule.right.len == 2 && rule.right.hasAnyIn(terminals) && rule.right.hasAnyIn(nonTerminals)) ||
    (rule.right.len == 1 && rule.right.allIn(terminals))))

  override def toString: String =
    s"""Terminals: ${terminals.mkString}
      |NonTerminals: ${nonTerminals.mkString}
      |Rules: ${rules.mkString("\n\t", ";\n\t", "")}
      |Start symbol: $startSymbol""".stripMargin
}
