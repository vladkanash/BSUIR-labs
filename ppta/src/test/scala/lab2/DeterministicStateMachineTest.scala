package lab2

import spec.GrammarStateMachineSpec

class DeterministicStateMachineTest extends GrammarStateMachineSpec {

  private def isDeterministic(machine: FiniteStateMachine) = machine.transitions
    .groupBy(tr => (tr.inputState, tr.inputSymbol))
    .values.forall(_.size == 1)

  "A deterministic state machine" should "have only deterministic transitions" in {
    val machine = deterministicMachineFromGrammar("grammar/testGrammar7.txt")

    assert(isDeterministic(machine), "State machine has non-deterministic transitions")
  }

  it should "be successfully created from deterministic grammar" in {
    val machine = deterministicMachineFromGrammar("grammar/testGrammar11.txt")

    assert(isDeterministic(machine), "State machine has non-deterministic transitions")
  }
}
