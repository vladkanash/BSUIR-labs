package parser.statemachine

import spec.StateMachineSpec

class StateMachineParserTest extends StateMachineSpec {

  "A state machine parser" should "parse state machine from file" in {
    val result = parseFromFile("machine/testMachine1.txt")

    assert(result.nonEmpty)
    assert(result.get.states.size == 10)
  }

  it should "not recognize state machine with invalid transition syntax" in {
    val result = parseFromFile("machine/testMachine2.txt")

    assert(result.isEmpty)
  }
}
