package ft


import ft.transform.{FFT, DFT}

import scala.swing._
import scala.swing.event.MousePressed

import Swing._
/**
  * Created by vladkanash on 2/23/16.
  */
object SwingGUI extends SimpleSwingApplication {

  def top = new MainFrame {

    title = "Fourier transform"

    val label = new Label {
      text = "Hello"
      listenTo(mouse.clicks)
      reactions += {
        case MousePressed(_, _, _, _, _) =>
          println("Mouse pressed2")
      }
    }

    val panel = new BoxPanel(Orientation.Vertical) {

      contents += new Label("DFT multiplications: " + FTCharts.getDFTMultiplications)
      contents += VStrut(10)
      contents += new Label("DFT additions: " + FTCharts.getDFTAdditions)
      contents += VStrut(10)
      contents += new Label("FFT multiplications: " + FTCharts.getFFTAdditions)
      contents += VStrut(10)
      contents += new Label("FFT additions: " + FTCharts.getFFTAdditions)
      contents += VStrut(10)
    }

    contents = new TabbedPane {
      pages += new TabbedPane.Page("Time chart", FTCharts.getTimeChart)
      pages += new TabbedPane.Page("Frequency chart", FTCharts.getFrequencyChart(FFT))
      pages += new TabbedPane.Page("Phase chart", FTCharts.getPhaseChart(FFT))
      pages += new TabbedPane.Page("Phase chart", FTCharts.getPhaseChart(DFT))
      pages += new TabbedPane.Page("Page 2", panel)


    }
  }



}
