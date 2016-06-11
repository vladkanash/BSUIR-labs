package dsp.lab3

import dsp.lab3.transform.FWT
import scala.swing._

import Swing._
/**
  * Created by vladkanash on 2/23/16.
  */
object SwingGUI extends SimpleSwingApplication {

  def top = new MainFrame {

    title = "Walsh transform"

    val statsPanel = new BoxPanel(Orientation.Vertical) {
      contents += VStrut(10)
      contents += new Label("N = " + WTCharts.N)
    }

    contents = new TabbedPane {
      pages += new TabbedPane.Page("Initial signal", WTCharts.getTimeChart)
      pages += new TabbedPane.Page("Frequency chart (FWT)", WTCharts.getFrequencyChart(FWT))
      pages += new TabbedPane.Page("Restored signal (FWT)", WTCharts.getRevertChart(FWT))
      pages += new TabbedPane.Page("Statistics", statsPanel)
    }
  }
}