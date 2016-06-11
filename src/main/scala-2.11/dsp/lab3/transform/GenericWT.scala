package dsp.lab3.transform

import dsp.util.Complex

/**
  * Created by vladkanash on 2/16/16.
  */
trait GenericWT {

  def apply(list: List[Complex], dir: Boolean = false): List[Complex]
}
