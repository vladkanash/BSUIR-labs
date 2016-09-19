package com.bsuir.modeling.lab1.gui;

import com.bsuir.modeling.lab1.constants.GUIConstants;
import com.bsuir.modeling.lab1.chart.ChartService;
import com.bsuir.modeling.lab1.random.LehmerRandomGenerator;
import com.bsuir.modeling.lab1.random.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by Vlad Kanash on 12.9.16.
 */
public class ChartGUI {

    private final static JLabel expectedValue = new JLabel();
    private final static JLabel variance = new JLabel();
    private final static JLabel period = new JLabel();
    private final static JLabel check = new JLabel();
    private final static JLabel aperiod = new JLabel();
    private final static JLabel standardDeviation = new JLabel();

    private final static JPanel chartBlock = new JPanel();
    private final static JPanel infoBlock = new JPanel();
    private final static JPanel inputBlock = new JPanel();
    private final static JPanel bottomBlock = new JPanel();

    public static void init(RandomGenerator generator) {
        SwingUtilities.invokeLater(() -> {
            initInfoBlock();
            initInputBlock(generator);
            initBottomBlock();
            initChartBlock();
            initFrame();
        });
    }

    private static void initBottomBlock() {
        bottomBlock.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomBlock.setBackground(Color.WHITE);
        bottomBlock.add(inputBlock, BorderLayout.WEST);
        bottomBlock.add(infoBlock, BorderLayout.WEST);
    }

    private static void initChartBlock() {

        final JPanel chartPanel = getNewChartPanel(Collections.emptyMap());

        chartBlock.setLayout(new BorderLayout());
        chartBlock.add(chartPanel);
    }

    private static void initInputBlock(RandomGenerator generator) {
        inputBlock.setLayout(new BoxLayout(inputBlock, BoxLayout.PAGE_AXIS));
        inputBlock.setBackground(Color.WHITE);
        inputBlock.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inputPanel = null;

        for (Map.Entry<String, Double> entry : generator.getInitParams().entrySet()) {
            final JTextField input = initTextField(entry.getValue());
            inputPanel = initInputPanel(input, entry.getKey());
            inputBlock.add(inputPanel);
        }

        setInputVerifiers();

        if (inputPanel != null) {
            inputPanel.add(new JButton(new AbstractAction(GUIConstants.BUTTON_LABEL) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateData(generator);
                }
            }));
        }
    }

    private static void updateData(RandomGenerator generator) {

        Set<String> paramNames = generator.getInitParams().keySet();
        Map<String, Double> newParams = new HashMap<>();

        for (Component inputPanel : inputBlock.getComponents()) {
            String name = inputPanel.getName();
            if (inputPanel instanceof JPanel && paramNames.contains(name)) {
                for (Component textField : ((JPanel) inputPanel).getComponents()) {
                    if (textField instanceof JTextField ) {
                        newParams.put(name, Double.valueOf(((JTextField) textField).getText()));
                    }
                }
            }
        }

        chartBlock.removeAll();
        chartBlock.revalidate();
        chartBlock.setLayout(new BorderLayout());
        chartBlock.add(getNewChartPanel(newParams));
        chartBlock.repaint();
    }

    private static JPanel initInputPanel(JTextField inputR, String text) {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(inputR);
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(GUIConstants.ENTER_LABEL_TEXT + text));
        panel.setName(text);
        return panel;
    }

    private static void initInfoBlock() {
        infoBlock.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoBlock.setLayout(new BoxLayout(infoBlock, BoxLayout.PAGE_AXIS));
        infoBlock.setBackground(Color.WHITE);
        infoBlock.add(expectedValue);
        infoBlock.add(variance);
        infoBlock.add(standardDeviation);
        infoBlock.add(check);
        infoBlock.add(period);
        infoBlock.add(aperiod);
    }

    private static void setInputVerifiers() {
        final InputVerifier verifier = new NotEmptyInputVerifier();
        expectedValue.setInputVerifier(verifier);
        variance.setInputVerifier(verifier);
        standardDeviation.setInputVerifier(verifier);
        check.setInputVerifier(verifier);
        period.setInputVerifier(verifier);
        aperiod.setInputVerifier(verifier);
    }

    private static JTextField initTextField(double value) {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        final DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        final JTextField input = new JFormattedTextField(decimalFormat);
        input.setText(String.valueOf(value));
        input.setColumns(10);
        return input;
    }

    private static JPanel getNewChartPanel(Map<String, Double> params) {
        RandomGenerator generator = new LehmerRandomGenerator(params);
        final double[] values = generator.getStream().limit(GUIConstants.RANDOM_LIMIT).toArray();
        updateLabels(values, generator);
        return ChartService.generateFrequencyHistogramPanel(values);
    }

    private static void updateLabels(double[] values, RandomGenerator generator) {
        expectedValue.setText(LabelUtils.getExpectedValueLabel(values));
        variance.setText(LabelUtils.getVarianceString(values));
        check.setText(LabelUtils.getCheckString(values));
        aperiod.setText(LabelUtils.getAPeriodString(generator));
        period.setText(LabelUtils.getPeriodString(generator));
        standardDeviation.setText(LabelUtils.getSkoString(values));
    }

    private static void initFrame() {
        JFrame frame = new JFrame(GUIConstants.GUI_WINDOW_NAME);
        frame.setSize(GUIConstants.GUI_WINDOW_WIDTH, GUIConstants.GUI_WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Container contentPane = frame.getContentPane();
        contentPane.add(chartBlock);
        contentPane.add(bottomBlock);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    }
}