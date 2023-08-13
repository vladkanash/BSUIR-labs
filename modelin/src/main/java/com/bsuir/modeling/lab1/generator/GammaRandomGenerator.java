package com.bsuir.modeling.lab1.generator;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Created by vladkanash on 26.9.16.
 */
public class GammaRandomGenerator extends RandomGenerator {

    private final static double INIT_LAMBDA = 16;
    private final static double INIT_N = 10;

    private final Random basicGenerator = new Random();

    public GammaRandomGenerator(Map<String, Double> params) {
        if (params == null || params.size() != 2) {
            this.params.put(GeneratorConstants.LAMBDA_PARAM_NAME, INIT_LAMBDA);
            this.params.put(GeneratorConstants.N_PARAM_NAME, INIT_N);
        } else {
            this.params.putAll(params);
        }
    }

    public GammaRandomGenerator() {
        this(Collections.emptyMap());
    }

    @Override
    public DoubleStream getStream() {
        final double lambda = params.getOrDefault(GeneratorConstants.LAMBDA_PARAM_NAME, INIT_LAMBDA);
        final int n = params.getOrDefault(GeneratorConstants.N_PARAM_NAME, INIT_N).intValue();
        return DoubleStream.generate( () ->
                (-1) / lambda * Math.log(basicGenerator.doubles(n).reduce((a, b) -> a * b).getAsDouble()));
    }
}
