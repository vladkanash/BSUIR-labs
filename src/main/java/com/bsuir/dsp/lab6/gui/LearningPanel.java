package com.bsuir.dsp.lab6.gui;

import com.bsuir.dsp.lab6.ImageProcessor;
import com.bsuir.dsp.lab6.neural.HopfieldNetwork;
import com.bsuir.dsp.lab6.neural.TrainingImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.bsuir.dsp.lab6.Constants.*;

public class LearningPanel extends JPanel {

    private final HopfieldNetwork network = new HopfieldNetwork(N);
    private final JFileChooser fileChooser = new JFileChooser();

    private final JLabel addedImage = new JLabel();
    private final JLabel preparedImage = new JLabel();
    private final JLabel matchedImage = new JLabel();

    public LearningPanel(final JPanel imageList) {
        super();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files",
                "png", "jpg", "jpeg", "bmp"));

        final JButton chooseFile = new JButton(new AbstractAction("Select image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image = selectImage();
                ImageProcessor processor = new ImageProcessor(image, N, N);

                imageList.add(new JLabel(new ImageIcon(processor.getImage())));
                addedImage.setIcon(new ImageIcon(processor.getImage()));
                preparedImage.setIcon(new ImageIcon(processor.getResizedPreparedImage()));

                network.learn(processor.getTrainingImage("Image1"));
            }
        });

        final JButton processFile = new JButton(new AbstractAction("Process Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                final BufferedImage image = selectImage();
                ImageProcessor processor = new ImageProcessor(image, N, N);
                final TrainingImage match = network.process(processor.getPixelArray());
                if (match != null) {
                    preparedImage.setIcon(null);
                    addedImage.setIcon(null);
                    matchedImage.setIcon(new ImageIcon(match.getImage()));
                } else {
                    //set error icon
                }
            }
        });

        final JPanel buttonsBlock = new JPanel();
        buttonsBlock.setLayout(new BoxLayout(buttonsBlock, BoxLayout.PAGE_AXIS));
        buttonsBlock.add(chooseFile);
        buttonsBlock.add(processFile);

        final JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout());
        imagesPanel.add(addedImage);
        imagesPanel.add(preparedImage);
        imagesPanel.add(matchedImage);

        this.setLayout(new BorderLayout());
        this.add(buttonsBlock, BorderLayout.WEST);
        this.add(imagesPanel, BorderLayout.CENTER);

    }

    private BufferedImage selectImage() {
        int result = fileChooser.showOpenDialog(this);
        if (JFileChooser.APPROVE_OPTION != result) {
            return null;
        }

        final File imageFile = fileChooser.getSelectedFile();
        try {
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
