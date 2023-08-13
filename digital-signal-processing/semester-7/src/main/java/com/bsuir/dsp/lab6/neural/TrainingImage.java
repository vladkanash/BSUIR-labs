package com.bsuir.dsp.lab6.neural;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by vladkanash on 25.11.16.
 */
public class TrainingImage {

    private final double[] imageData;
    private final String name;
    private final BufferedImage image;

    public TrainingImage(double[] imageData, String name, BufferedImage image) {
        this.imageData = imageData;
        this.name = name;
        this.image = image;
    }

    public double[] getImageData() {
        return imageData;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingImage that = (TrainingImage) o;

        if (!Arrays.equals(imageData, that.imageData)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(imageData);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
