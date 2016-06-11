package dsp.lab4

import scala.swing._

/**
  * Created by vladkanash on 2/23/16.
  */
object SwingGUI extends SimpleSwingApplication {

  def top = new MainFrame {

    title = "Wavelet transform"

    contents = new TabbedPane {
      pages += new TabbedPane.Page("Initial signal", WTCharts.getTimeChart)
      pages += new TabbedPane.Page("Wavelet transform (step 1)", WTCharts.getWaveletChart(1))
      pages += new TabbedPane.Page("Wavelet transform (step 2)", WTCharts.getWaveletChart(2))
      pages += new TabbedPane.Page("Wavelet transform (step 3)", WTCharts.getWaveletChart(3))
      pages += new TabbedPane.Page("Wavelet transform (step 4)", WTCharts.getWaveletChart(4))
      pages += new TabbedPane.Page("Restored signal (FFT)", WTCharts.getRevertChart(4))
    }
  }
}
