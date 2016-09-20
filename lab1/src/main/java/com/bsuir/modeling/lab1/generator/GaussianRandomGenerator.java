package com.bsuir.modeling.lab1.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Created by root on 20.9.16.
 */
public class GaussianRandomGenerator extends RandomGenerator {

    private final static double INIT_SIGMA = 2177;
    private final static double INIT_M = 811;

    private final Random basicGenerator = new Random();
    private final double sigma;
    private final double m;

    public GaussianRandomGenerator(Map<String, Double> params) {
        final Map<String, Double> clonedParams = new HashMap<>();

        if (params == null || params.size() != 2) {
            clonedParams.put(GeneratorConstants.SIGMA_PARAM_NAME, INIT_SIGMA);
            clonedParams.put(GeneratorConstants.M_PARAM_NAME, INIT_M);
        } else {
            clonedParams.putAll(params);
        }

        this.params = clonedParams;
        this.sigma = clonedParams.getOrDefault(GeneratorConstants.SIGMA_PARAM_NAME, 1.0);
        this.m = clonedParams.getOrDefault(GeneratorConstants.M_PARAM_NAME, 1.0);
    }

    public GaussianRandomGenerator() {
        this(Collections.emptyMap());
    }

    @Override
    public DoubleStream getStream() {
        return DoubleStream.generate(() ->
                this.m + this.sigma * Math.sqrt(2) * (basicGenerator.doubles(6).sum() - 3));
    }
}
