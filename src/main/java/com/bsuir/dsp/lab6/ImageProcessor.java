package com.bsuir.dsp.lab6;

import com.bsuir.dsp.lab6.neural.TrainingImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import static com.bsuir.dsp.lab6.Constants.BINARY_THRESHOLD_VALUE;
import static com.bsuir.dsp.lab6.Constants.BIPOLAR_NEGATIVE;
import static com.bsuir.dsp.lab6.Constants.BIPOLAR_POSITIVE;

/**
 * This class prepares the image file for using as training set in neural network.
 */
public class ImageProcessor {

    /**
     * Initial image.
     */
    private final BufferedImage image;

    /**
     * Grayscaled image (each byte is one pixel).
     */
    private final BufferedImage grayscaledImage;

    /**
     * Image, prepared for using as training set.
     */
    private final BufferedImage preparedImage;

    /**
     * Resized grayscaled image (for presentation)
     */
    private final BufferedImage resizedGrayscaledImage;

    /**
     * Rescaled prepared image (for presentation).
     */
    private final BufferedImage resizedPreparedImage;

    /**
     * Matrix representation of prepared image.
     */
    private final double[][] pixelMatrix;

    /**
     * Array representation of prepared image.
     */
    private final double[] pixelArray;

    public ImageProcessor(final BufferedImage image, int newWidth, int newHeight) {
        this.image = image;
        this.grayscaledImage = rescaleImage(image, newWidth, newHeight);
        this.resizedGrayscaledImage = rescaleImage(grayscaledImage, image.getWidth(), image.getHeight());
        this.pixelMatrix = imageToBinaryMatrix(grayscaledImage);
        this.pixelArray = imageToBinaryArray(grayscaledImage);
        this.preparedImage = getImageFromArray(pixelMatrix);
        this.resizedPreparedImage = rescaleImage(preparedImage, image.getWidth(), image.getHeight());
    }

    /**
     * Grayscales image and changes image resolution.
     * @param image input image
     * @param newWidth new image width
     * @param newHeight new image height
     * @return rescaled image
     */
    private BufferedImage rescaleImage(BufferedImage image, int newWidth, int newHeight) {
        final BufferedImage rescaledImage =
                new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        final Graphics rescaledGraphics = rescaledImage.getGraphics();
        rescaledGraphics.drawImage(image, 0, 0, newWidth, newHeight,  null);
        rescaledGraphics.dispose();
        return rescaledImage;
    }

    /**
     * Converts grayscale image to binary matrix (each byte is one pixel).
     * @param image input image
     * @return matrix of 0's and 1's
     */
    private double[][] imageToBinaryMatrix(final BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            return null;
        }

        final WritableRaster raster =image.getRaster();
        final byte[] data = ((DataBufferByte) raster.getDataBuffer()).getData();
        final double[][] pixelMatrix = new double[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if ((byte)(data[i * image.getWidth() + j] + Byte.MIN_VALUE) > BINARY_THRESHOLD_VALUE) {
                    pixelMatrix[i][j] = BIPOLAR_POSITIVE;
                } else {
                    pixelMatrix[i][j] = BIPOLAR_NEGATIVE;
                }
            }
        }
        return pixelMatrix;
    }

    /**
     * Converts grayscale image to array.
     * @param image input image
     * @return array of image pixels
     */
    private double[] imageToBinaryArray(final BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            return null;
        }

        final WritableRaster raster =image.getRaster();
        final byte[] imageData = ((DataBufferByte) raster.getDataBuffer()).getData();
        final double[] pixelArray = new double[imageData.length];

        for (int i = 0; i < imageData.length; i++) {
            if ((byte)(imageData[i] + Byte.MIN_VALUE) > BINARY_THRESHOLD_VALUE) {
                pixelArray[i] = BIPOLAR_POSITIVE;
            } else {
                pixelArray[i] = BIPOLAR_NEGATIVE;
            }
        }
        return pixelArray;
    }

    /**
     * Converts byte matrix to image.
     * @param pixels 2D array of image pixels.
     * @return image
     */
    private BufferedImage getImageFromArray(double[][] pixels) {
        final int width = pixels[0].length;
        final int height = pixels.length;
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        final WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0, width, height, convertToIntArray(pixels));
        image.setData(raster);
        return image;
    }

    /**
     * Converts 2D byte array to one-dimensional int array.
     * @param input 2D byte array
     * @return int array
     */
    private static int[] convertToIntArray(double[][] input) {
        final int[] result = new int[input.length * input[0].length];
        int x, y;
        for (int i = 0; i < result.length; i++) {
            y = i / input.length;
            x = i % input.length;
            result[i] = (int)(input[y][x] * 255);
        }
        return result;
    }

    /**
     * Generates TrainingImage bean from data stored in image processor;
     * @param name name of the new TrainingImage
     * @return TrainingImage object
     */
    public TrainingImage getTrainingImage(final String name) {
        return new TrainingImage(pixelArray, name, image);
    }

    public final BufferedImage getImage() {
        return image;
    }

    public final BufferedImage getPreparedImage() {
        return preparedImage;
    }

    public final BufferedImage getResizedPreparedImage() {
        return resizedPreparedImage;
    }

    public final BufferedImage getGrayscaledImage() {
        return grayscaledImage;
    }

    public final BufferedImage getResizedGrayscaledImage() {
        return resizedGrayscaledImage;
    }

    public final double[][] getPixelMatrix() {
        return pixelMatrix;
    }

    public final double[] getPixelArray() {
        return pixelArray;
    }
}