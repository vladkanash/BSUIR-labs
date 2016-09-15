package com.bsuir.modeling.lab1.random;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class LehmerRandomGenerator implements RandomGenerator {

	private double r;
	private final double m;
	private final DoubleUnaryOperator lehmerTransform;

    public LehmerRandomGenerator(double r, double a, double m) {
		this.m = m == 0.0 ? 1.0 : m;
    	this.r = r;
    	this.lehmerTransform = e -> (a * e) % this.m;
    }

    public double nextRandom() {
    	r = lehmerTransform.applyAsDouble(r);
    	return r; 
    }

	@Override
	public DoubleStream getStream() {
    	return DoubleStream.iterate(r, lehmerTransform).map(e -> e / m).skip(1);
    }	
}
