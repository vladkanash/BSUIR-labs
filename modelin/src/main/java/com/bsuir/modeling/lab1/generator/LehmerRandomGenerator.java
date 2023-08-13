package com.bsuir.modeling.lab1.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

/**
 * Created by Vlad Kanash on 2.9.16.
 */
public class LehmerRandomGenerator extends RandomGenerator {

	private final static double INIT_R = 2177;
	private final static double INIT_A = 811;
	private final static double INIT_M = 256 * 256 * 2;

	private double r;
	private final double m;
	private final DoubleUnaryOperator lehmerTransform;

    public LehmerRandomGenerator(Map<String, Double> params) {
		final Map<String, Double> clonedParams = new HashMap<>();

		if (params == null || params.size() != 3) {
			clonedParams.put(GeneratorConstants.R_PARAM_NAME, INIT_R);
			clonedParams.put(GeneratorConstants.A_PARAM_NAME, INIT_A);
			clonedParams.put(GeneratorConstants.M_PARAM_NAME, INIT_M);
		} else {
			clonedParams.putAll(params);
		}

		this.params = clonedParams;

		Double r = clonedParams.getOrDefault(GeneratorConstants.R_PARAM_NAME, 1.0);
		Double a = clonedParams.getOrDefault(GeneratorConstants.A_PARAM_NAME, 0.0);
		Double m = clonedParams.getOrDefault(GeneratorConstants.M_PARAM_NAME, 1.0);

		this.m = m == 0 ? 1.0 : m;
    	this.r = r;
    	this.lehmerTransform = e -> (a * e) % this.m;
    }

	public LehmerRandomGenerator() {
		this(Collections.emptyMap());
	}

	@Override
	public DoubleStream getStream() {
    	return DoubleStream.iterate(r, lehmerTransform).map(e -> e / m).skip(1);
    }
}
