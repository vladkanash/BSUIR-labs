package dsp.lab2

import dsp.GenericSpec

/**
  * Created by vladkanash on 3/13/16.
  */
class ConvolutionSpec extends GenericSpec {

  "A convolution function" should "produce the correct result" in {
    val input = List(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1)
    val output = Convolution(input, input).map(truncate(_, 3))

    print(output)
  }
}
