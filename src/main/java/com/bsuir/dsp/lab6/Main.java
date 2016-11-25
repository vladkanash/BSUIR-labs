package com.bsuir.dsp.lab6;

import com.bsuir.dsp.lab6.gui.LearningPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladkanash on 24.11.16.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JTabbedPane tabbedPane = new JTabbedPane();
            JPanel imageList = new JPanel();
            JScrollPane scrollPane = new JScrollPane(imageList);
            imageList.setLayout(new BoxLayout(imageList, BoxLayout.PAGE_AXIS));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tabbedPane.addTab("Learning", new LearningPanel(imageList));
            tabbedPane.addTab("Learned Images", scrollPane);

            JFrame frame = new JFrame("Hopfield Network");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            Container contentPane = frame.getContentPane();
            contentPane.add(tabbedPane);
        });
    }
}
