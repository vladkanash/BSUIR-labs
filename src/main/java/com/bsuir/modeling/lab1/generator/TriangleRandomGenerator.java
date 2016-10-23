package com.bsuir.modeling.lab1.generator;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Created by vladkanash on 26.9.16.
 */
public class TriangleRandomGenerator extends RandomGenerator {

    private final static double INIT_A = 0;
    private final static double INIT_B = 1;

    private final static Random basicGenerator = new Random();

    public TriangleRandomGenerator(Map<String, Double> params) {
        if (params == null || params.size() != 2) {
            this.params.put(GeneratorConstants.A_PARAM_NAME, INIT_A);
            this.params.put(GeneratorConstants.B_PARAM_NAME, INIT_B);
        } else {
            this.params.putAll(params);
        }
    }

    public TriangleRandomGenerator() {
        this(Collections.emptyMap());
    }

    @Override
    public DoubleStream getStream() {
        final double a = params.getOrDefault(GeneratorConstants.A_PARAM_NAME, INIT_A);
        final double b = params.getOrDefault(GeneratorConstants.B_PARAM_NAME, INIT_B);
        return DoubleStream.generate(() -> a + (b - a) * basicGenerator.doubles(2).min().getAsDouble());
    }
}
