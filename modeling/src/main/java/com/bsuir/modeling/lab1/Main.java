package com.bsuir.modeling.lab1;

import com.bsuir.modeling.lab1.constants.GUIConstants;
import com.bsuir.modeling.lab1.generator.*;
import com.bsuir.modeling.lab1.gui.GeneratorPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vlad Kanash on 2.9.16.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab(GUIConstants.LEHMER_TAB_NAME, new GeneratorPanel(new LehmerRandomGenerator(), true));
            tabbedPane.addTab(GUIConstants.GAUSSIAN_TAB_NAME, new GeneratorPanel(new GaussianRandomGenerator(), false));
            tabbedPane.addTab(GUIConstants.EXPONENTIAL_TAB_NAME, new GeneratorPanel(new ExponentialRandomGenerator(), false));
            tabbedPane.addTab(GUIConstants.UNIFORM_TAB_NAME, new GeneratorPanel(new UniformRandomGenerator(), false));
            tabbedPane.addTab(GUIConstants.SIMPSON_TAB_NAME, new GeneratorPanel(new SimpsonRandomGenerator(), false));
            tabbedPane.addTab(GUIConstants.TRIANGLE_TAB_NAME, new GeneratorPanel(new TriangleRandomGenerator(), false));
            tabbedPane.addTab(GUIConstants.GAMMA_TAB_NAME, new GeneratorPanel(new GammaRandomGenerator(), false));

            JFrame frame = new JFrame(GUIConstants.GUI_WINDOW_NAME);
            frame.setSize(GUIConstants.GUI_WINDOW_WIDTH, GUIConstants.GUI_WINDOW_HEIGHT);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            Container contentPane = frame.getContentPane();
            contentPane.add(tabbedPane);
        });
    }
}
