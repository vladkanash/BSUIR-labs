package com.bsuir.modeling.lab1.generator;

import java.util.Map;
import java.util.stream.DoubleStream;

/**
 * Created by Vlad Kanash on 20.9.16.
 */
public class SimpsonRandomGenerator extends UniformRandomGenerator {

    public SimpsonRandomGenerator(Map<String, Double> params) {
        super(params);
        this.params.replace(GeneratorConstants.A_PARAM_NAME, this.params.get(GeneratorConstants.A_PARAM_NAME) / 2);
        this.params.replace(GeneratorConstants.B_PARAM_NAME, this.params.get(GeneratorConstants.B_PARAM_NAME) / 2);
    }

    public SimpsonRandomGenerator() {
        super();
    }

    @Override
    public DoubleStream getStream() {
        return DoubleStream.generate(() -> super.getStream().limit(2).sum());
    }
}
