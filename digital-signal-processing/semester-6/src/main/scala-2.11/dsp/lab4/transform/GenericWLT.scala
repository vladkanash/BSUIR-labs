package dsp.lab4.transform

import dsp.util.Complex

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericWLT {

  def apply(list: List[Complex],
            depth: Int,
            dir: Boolean = false,
            highs: List[List[Complex]] = List.empty): List[Complex]
}
