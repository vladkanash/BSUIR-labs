package com.bsuir.modeling.lab1.random;

import java.util.Map;
import java.util.stream.DoubleStream;

/**
 * Created by Vlad Kanash on 12.9.16.
 */
public interface RandomGenerator {

    DoubleStream getStream();

    Map<String, Double> getInitParams();
}
