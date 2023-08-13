package com.bsuir.modeling.lab1.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;

/**
 * Created by Vlad Kanash on 12.9.16.
 */
public abstract class RandomGenerator {

    Map<String, Double> params = new HashMap<>();

    public abstract DoubleStream getStream();

    public Map<String, Double> getInitParams() {
        return params;
    }
}
