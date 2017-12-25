package lab4

import spec.GrammarSpec

class AutomaticMemoryMachineTest extends GrammarSpec {

  "Automatic memory machine" should "recognize valid input string" in {
    val grammar = parseFromFile("grammar/testGrammar8.txt")
    val machine = AutomaticMemoryMachine(grammar.get)

    val input = "a+a"
    assert(machine.parse(input), "The input " + input + " should be successfully parsed with this state machine")
  }
}
