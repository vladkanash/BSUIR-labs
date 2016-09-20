package com.bsuir.modeling.lab1.gui;

import com.bsuir.modeling.lab1.constants.GUIConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;

/**
 * Created by Vlad Kanash on 15.9.16.
 */
public class ChartService {
    public static JPanel generateFrequencyHistogramPanel(double[] values) {

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries(GUIConstants.GUI_PLOT_TITLE, values, GUIConstants.HISTOGRAM_COLUMNS_NUM);

        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = false;
        boolean toolTips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createHistogram("", "", "",
                dataset, orientation, legend, toolTips, urls);

        return new ChartPanel(chart);
    }
}
