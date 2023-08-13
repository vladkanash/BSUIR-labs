package dsp.lab2

import dsp.GenericSpec
import dsp.lab2.transform.{DiscreteCorrelation, DiscreteConvolution, Convolution}

/**
  * Created by vladkanash on 3/13/16.
  */
class ConvolutionSpec extends GenericSpec {

  "A convolution function" should "produce the correct result" in {
    val input = List(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0)
    val output = Convolution(input, input).map(truncate(_, 3))
  }

  it should "produce the same result with discrete convolution" in {
    val input = List(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0)
    val output1 = Convolution(input, input).map(truncate(_, 3))
    val output2 = DiscreteConvolution(input, input).map(truncate(_, 3))

    output1 shouldEqual output2
  }
}
