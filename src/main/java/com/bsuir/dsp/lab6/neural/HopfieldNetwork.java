package com.bsuir.dsp.lab6.neural;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static com.bsuir.dsp.lab6.Constants.PROCESSING_STEPS;

public class HopfieldNetwork {

    private RealMatrix w;
    private final int n;
    private final Set<TrainingImage> imageSet = new HashSet<>();

    public HopfieldNetwork(final int n) {
        this.n = n;
        this.w = MatrixUtils.createRealMatrix(n * n, n * n);
    }

    public void learn(TrainingImage image) {
        final double[] data = image.getImageData();
        if (data.length != w.getRowDimension() ||
            data.length != w.getColumnDimension()) {
            System.out.println("Error. Invalid training set size.");
            return;
        }

        final RealVector trainingSet = MatrixUtils.createRealVector(data);
        final RealMatrix trainingSetMatrix = trainingSet.outerProduct(trainingSet);
        IntStream.range(0, n * n).forEach(e -> trainingSetMatrix.setEntry(e, e, 0));
        w = w.add(trainingSetMatrix.scalarMultiply(1.0 / n));
        imageSet.add(image);
    }

    /**
     * Goes through a number of processing iterations and tries to recognize
     * a training image example in the input data.
     * @param inputData input data
     * @return TrainingImage if a match was found, null otherwise
     */
    public TrainingImage process(double[] inputData) {
        RealVector nextStep = MatrixUtils.createRealVector(inputData);

        for (int i = 0; i < PROCESSING_STEPS; i++) {
            nextStep = w.operate(nextStep);
            nextStep.mapToSelf(e -> e > 0 ? 1 : -1);
        }
        return findImage(nextStep.toArray());
    }

    private TrainingImage findImage(double[] image) {
        return imageSet.stream()
                .filter(e -> Arrays.equals(image, e.getImageData()))
                .findAny().orElse(null);
    }
}
