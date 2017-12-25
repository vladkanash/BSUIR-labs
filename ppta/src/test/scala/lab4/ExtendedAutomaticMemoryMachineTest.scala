package lab4

import spec.GrammarSpec

class ExtendedAutomaticMemoryMachineTest extends GrammarSpec {

  "Extended automatic memory machine" should "recognize valid input string" in {
    val grammar = parseFromFile("grammar/testGrammar8.txt")
    val machine = ExtendedAutomaticMemoryMachine(grammar.get)

    val input = "a+(a+(a-a))"
    assert(machine.parse(input), "The input " + input + " should be successfully parsed with this state machine")
  }

  it should "not recognize invalid input string" in {
    val grammar = parseFromFile("grammar/testGrammar12.txt")
    val machine = ExtendedAutomaticMemoryMachine(grammar.get)

    val input = "papgggg"
    assert(!machine.parse(input), "The input " + input + " should not be successfully parsed with this state machine")
  }
}
