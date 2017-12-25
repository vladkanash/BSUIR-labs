package lab1

import spec.GrammarSpec

class GrammarTest extends GrammarSpec {

  "A grammar" should "not be crated when rules contain invalid symbols" in {
    assertThrows[IllegalArgumentException] {
      parseFromFile("grammar/testGrammar5.txt")
    }
  }

  it should "not be created where terminals and non-terminals share same symbols" in {
    assertThrows[IllegalArgumentException] {
      parseFromFile("grammar/testGrammar6.txt")
    }
  }

  it should "have regular type when rules are matching regular grammar requirements" in {
    val result = parseFromFile("grammar/testGrammar7.txt")

    assert(result.nonEmpty)
    assert(result.get.grammarType == GrammarType.Regular)
  }

  it should "have context-free type when rules are matching context-free grammar requirements" in {
    val result = parseFromFile("grammar/testGrammar8.txt")

    assert(result.nonEmpty)
    assert(result.get.grammarType == GrammarType.ContextFree)
  }

  it should "have context-dependent type when rules are matching context-dependent grammar requirements" in {
    val result = parseFromFile("grammar/testGrammar9.txt")

    assert(result.nonEmpty)
    assert(result.get.grammarType == GrammarType.ContextDependent)
  }

  it should "belong to type 0 when rules are not matching any other type requirements" in {
    val result = parseFromFile("grammar/testGrammar10.txt")

    assert(result.nonEmpty)
    assert(result.get.grammarType == GrammarType.Type0)
  }
}
