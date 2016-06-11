package dsp.lab1

import dsp.lab1.transform.{DFT, FFT}

import scala.swing.Swing._
import scala.swing._

/**
  * Created by vladkanash on 2/23/16.
  */
object SwingGUI extends SimpleSwingApplication {

  def top = new MainFrame {

    title = "Fourier transform"

    val statsPanel = new BoxPanel(Orientation.Vertical) {

      contents += VStrut(10)
      contents += new Label("DFT multiplications: " + FTCharts.DFTMultiplications)
      contents += VStrut(10)
      contents += new Label("DFT additions: " + FTCharts.DFTAdditions)
      contents += VStrut(10)
      contents += new Label("FFT multiplications: " + FTCharts.FFTAdditions)
      contents += VStrut(10)
      contents += new Label("FFT additions: " + FTCharts.FFTAdditions)
      contents += VStrut(10)
      contents += new Label("N = " + FTCharts.N)
    }

    contents = new TabbedPane {
      pages += new TabbedPane.Page("Initial signal", FTCharts.getTimeChart)
      pages += new TabbedPane.Page("Frequency chart (FFT)", FTCharts.getFrequencyChart(FFT))
      pages += new TabbedPane.Page("Frequency chart (DFT)", FTCharts.getFrequencyChart(DFT))
      pages += new TabbedPane.Page("Restored signal (FFT)", FTCharts.getRevertChart(FFT))
      pages += new TabbedPane.Page("Phase chart (DFT)", FTCharts.getPhaseChart(DFT))
      pages += new TabbedPane.Page("Phase chart (FFT)", FTCharts.getPhaseChart(FFT))
      pages += new TabbedPane.Page("Statistics", statsPanel)
    }
  }
}
