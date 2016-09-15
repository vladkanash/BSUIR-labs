package com.bsuir.modeling.lab1.gui;

import com.bsuir.modeling.lab1.Constants;
import com.bsuir.modeling.lab1.chart.ChartService;
import com.bsuir.modeling.lab1.random.LehmerRandomGenerator;
import com.bsuir.modeling.lab1.random.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

    private static JTextField inputR;
    private static JTextField inputA;
    private static JTextField inputM;

    private final static JPanel chartBlock = new JPanel();
    private final static JPanel infoBlock = new JPanel();
    private final static JPanel inputBlock = new JPanel();
    private final static JPanel bottomBlock = new JPanel();

    public static void init() {
        SwingUtilities.invokeLater(() -> {
            initInfoBlock();
            initInputBlock();
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
        final JPanel chartPanel = getNewChartPanel
                (Constants.INIT_R, Constants.INIT_A, Constants.INIT_M );

        chartBlock.setLayout(new BorderLayout());
        chartBlock.add(chartPanel);
    }

    private static void initInputBlock() {
        inputBlock.setLayout(new BoxLayout(inputBlock, BoxLayout.PAGE_AXIS));

        inputR = initTextField(Constants.INIT_R);
        inputA = initTextField(Constants.INIT_A);
        inputM = initTextField(Constants.INIT_M);

        final JPanel rInputPanel = initInputPanel(inputR, Constants.R_INPUT_LABEL);
        final JPanel aInputPanel = initInputPanel(inputA, Constants.A_INPUT_LABEL);
        final JPanel mInputPanel = initInputPanel(inputM, Constants.M_INPUT_LABEL);

        inputBlock.setBackground(Color.WHITE);
        inputBlock.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputBlock.add(rInputPanel);
        inputBlock.add(aInputPanel);
        inputBlock.add(mInputPanel);

        setInputVerifiers();

        rInputPanel.add(new JButton(new AbstractAction(Constants.BUTTON_LABEL) {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        }));
    }

    private static void updateData() {
        final int R = Integer.valueOf(inputR.getText());
        final int A = Integer.valueOf(inputA.getText());
        final int M = Integer.valueOf(inputM.getText());

        chartBlock.removeAll();
        chartBlock.revalidate();
        chartBlock.setLayout(new BorderLayout());
        chartBlock.add(getNewChartPanel(R, A, M));
        chartBlock.repaint();
    }

    private static JPanel initInputPanel(JTextField inputR, String text) {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(inputR);
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(text));
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

    private static JTextField initTextField(int value) {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        final DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        final JTextField input = new JFormattedTextField(decimalFormat);
        input.setText(String.valueOf(value));
        input.setColumns(10);
        return input;
    }

    private static JPanel getNewChartPanel(int R, int A, int M) {
        RandomGenerator generator = new LehmerRandomGenerator(R, A, M);
        final double[] values = updateLabels(generator);
        return ChartService.generateFrequencyHistogramPanel(values);
    }

    private static double[] updateLabels(RandomGenerator generator) {
        final double[] values = generator.getStream().limit(Constants.RANDOM_LIMIT).toArray();

        expectedValue.setText(LabelUtils.getExpectedValueLabel(values));
        variance.setText(LabelUtils.getVarianceString(values));
        check.setText(LabelUtils.getCheckString(values));
        aperiod.setText(LabelUtils.getAPeriodString(generator));
        period.setText(LabelUtils.getPeriodString(generator));
        standardDeviation.setText(LabelUtils.getSkoString(values));
        return values;
    }

    private static void initFrame() {
        JFrame frame = new JFrame(Constants.GUI_WINDOW_NAME);
        frame.setSize(Constants.GUI_WINDOW_WIDTH, Constants.GUI_WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Container contentPane = frame.getContentPane();
        contentPane.add(chartBlock);
        contentPane.add(bottomBlock);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    }
}