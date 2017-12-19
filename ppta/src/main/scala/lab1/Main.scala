package lab1

import java.io.FileReader

import collection.JavaConverters._
import lab2.{Deterministic, FiniteStateMachine, GrammarFiniteStateMachine}
import lab3.Minimized
import lab4.{AutomaticMemoryMachine, ExtendedAutomaticMemoryMachine}
import parser.grammar.GrammarParser
import parser.statemachine.StateMachineParser
import viewer.StateMachineGraphViewer

object Main extends App {

  override def main(args: Array[String]): Unit = {

        val params = StateMachineParser.parse(new FileReader(args(0)))
        val machine = new FiniteStateMachine(params.get) with Minimized
        println(machine)

        new StateMachineGraphViewer(
          machine.transitions.asJava,
          machine.states.asJava,
          machine.startState,
          machine.endStates.asJava)

//    val grammar = GrammarParser.parse(new FileReader(args(0)))
//    println(grammar)
//
//    val extendedStoreMachine = ExtendedAutomaticMemoryMachine(grammar.get)
//    println(extendedStoreMachine)
//
//    val storeMachine = AutomaticMemoryMachine(grammar.get)
//    println(storeMachine)
//
//    val longInput = "(a)+a+(a-(a-a))-a"
//    val myInput = "papaggg"
//    extendedStoreMachine.parse(myInput)
//    storeMachine.parse(myInput)


  }

}
