package lab2

import spec.GrammarStateMachineSpec

class GrammarFiniteStateMachineTest extends GrammarStateMachineSpec {

  "A finite state machine (based on grammar)" should "not be constructed from non-regular grammar" in {
    assertThrows[IllegalArgumentException] {
      stateMachineFromGrammar("grammar/testGrammar8.txt")
    }
  }

  it should "be successfully created from regular grammar" in {
    val grammar = stateMachineFromGrammar("grammar/testGrammar7.txt")

    assert(grammar.transitions.size == 8)
  }
}
