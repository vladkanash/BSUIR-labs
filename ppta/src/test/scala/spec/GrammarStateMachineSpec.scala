package spec

import lab2.{Deterministic, GrammarFiniteStateMachine}

trait GrammarStateMachineSpec extends GrammarSpec {

  val failText = "Could not parse grammar"

  def stateMachineFromGrammar(filepath: String) =
    new GrammarFiniteStateMachine(parseFromFile(filepath)
      .getOrElse(fail(failText)))

  def deterministicMachineFromGrammar(filepath: String) =
    new GrammarFiniteStateMachine(parseFromFile(filepath)
      .getOrElse(fail(failText))) with Deterministic
}
