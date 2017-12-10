package lab1

import java.io.FileReader

import lab1.parser.GrammarParser
import lab2.{DeterminateFiniteStateMachine, FiniteStateMachine}

object Main extends App {

  override def main(args: Array[String]): Unit = {
    val grammar = GrammarParser.parse(new FileReader(args(0)))
    println(grammar)
    println(grammar.get.grammarType)

    val machine = new DeterminateFiniteStateMachine(grammar.get)
    println(machine)
  }

}
