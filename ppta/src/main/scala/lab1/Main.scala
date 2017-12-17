package lab1

import java.io.FileReader

import collection.JavaConverters._
import lab1.parser.GrammarParser
import lab2.{Deterministic, FiniteStateMachine}
import lab3.Minimized
import viewer.StateMachineGraphViewer

object Main extends App {

  override def main(args: Array[String]): Unit = {
    val grammar = GrammarParser.parse(new FileReader(args(0)))
    println(grammar)

    val machine = new FiniteStateMachine(grammar.get) with Deterministic with Minimized
    println(machine)

    new StateMachineGraphViewer(
      machine.transitions.asJava,
      machine.states.asJava,
      machine.startState,
      machine.endStates.asJava)
  }

}
