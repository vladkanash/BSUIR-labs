package com.bsuir.modeling.lab1.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Created by root on 20.9.16.
 */
public class ExponentialRandomGenerator extends RandomGenerator {

    private final static double INIT_LAMBDA = 54;

    private final double lambda;
    private final Random basicGenerator = new Random();

    public ExponentialRandomGenerator(Map<String, Double> params) {
        final Map<String, Double> clonedParams = new HashMap<>();

        if (params == null || params.size() != 1) {
            clonedParams.put(GeneratorConstants.LAMBDA_PARAM_NAME, INIT_LAMBDA);
        } else {
            clonedParams.putAll(params);
        }

        this.params = clonedParams;
        this.lambda = clonedParams.getOrDefault(GeneratorConstants.LAMBDA_PARAM_NAME, 1.0);
    }
    public ExponentialRandomGenerator() {
        this(Collections.emptyMap());
    }

    @Override
    public DoubleStream getStream() {
        return DoubleStream.generate(() ->
                (- 1) / this.lambda * Math.log(basicGenerator.nextDouble()));
    }
}
