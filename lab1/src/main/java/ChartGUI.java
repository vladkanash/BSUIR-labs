import org.apache.commons.math3.stat.StatUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Vlad Kanash on 12.9.16.
 */
public class ChartGUI {



    public static void init(double[] values) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = initFrame();

            JLabel expectedValue = getExpectedValueLabel(values);
            JLabel variance = getVarianceString(values);
            JFreeChart chart = generateChart(values);
            ChartPanel cp = new ChartPanel(chart);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.setBackground(Color.white);

            panel.add(cp);
            panel.add(expectedValue);
            panel.add(variance);
            panel.add(Box.createVerticalStrut(10));

            panel.add(new JButton(new AbstractAction("Horizontal") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    expectedValue.setText("AAAAAA!!!!");
                    panel.validate();
                }
            }));

            frame.getContentPane().add(panel);

        });
    }

    private static JLabel getExpectedValueLabel(double[] values) {
        return new JLabel(String.format("%s %.3f", Constants.EXPECTED_VALUE_LABEL,
                StatUtils.mean(values)));
    }

    private static JLabel getVarianceString(double[] values) {
        return new JLabel(String.format("%s %.3f", Constants.VARIANCE_LABEL,
                StatUtils.variance(values)));
    }

    private static JFrame initFrame() {
        JFrame frame = new JFrame(Constants.GUI_WINDOW_NAME);
        frame.setSize(Constants.GUI_WINDOW_WIDTH, Constants.GUI_WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    private static JFreeChart generateChart(double[] values) {

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries(Constants.GUI_PLOT_TITLE, values, Constants.HISTOGRAM_COLUMNS_NUM);

        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = false;
        boolean toolTips = false;
        boolean urls = false;

        return ChartFactory.createHistogram("",
                Constants.GUI_X_AXIS_LABEL,
                Constants.GUI_Y_AXIS_LABEL,
                dataset, orientation, legend, toolTips, urls);
    }

}
