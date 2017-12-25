package spec

import lab2.StateMachineParams
import parser.statemachine.StateMachineParser

trait StateMachineSpec extends BaseSpec {

  def parseFromFile(filepath: String): Option[StateMachineParams] =
    StateMachineParser.parse(readInput(filepath))
}
