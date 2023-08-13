package dsp.lab2

import scala.swing._

/**
  * Created by vladkanash on 3/14/16.
  */
object SwingGUI extends SimpleSwingApplication {

  def top = new MainFrame {

    title = "Correlation & Convolution"

    contents = new TabbedPane {
      pages += new TabbedPane.Page("Initial signal #1", Charts.getSignal1Chart)
      pages += new TabbedPane.Page("Initial signal #2", Charts.getSignal2Chart)
      pages += new TabbedPane.Page("Correlation", Charts.getCorrelationChart)
      pages += new TabbedPane.Page("Convolution", Charts.getConvolutionChart)

      pages += new TabbedPane.Page("Discrete Correlation", Charts.getDiscreteCorrelationChart)
      pages += new TabbedPane.Page("Discrete Convolution", Charts.getDiscreteConvolutionChart)
    }
  }
}
