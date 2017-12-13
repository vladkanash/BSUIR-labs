package lab1

import java.io.FileReader

import lab1.parser.GrammarParser
import lab2.{Deterministic, FiniteStateMachine}
object Main extends App {

  override def main(args: Array[String]): Unit = {
    val grammar = GrammarParser.parse(new FileReader(args(0)))
    println(grammar)

    val machine = new FiniteStateMachine(grammar.get) with Deterministic
    println(machine)
  }

}
