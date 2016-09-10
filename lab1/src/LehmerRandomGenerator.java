

import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class LehmerRandomGenerator {

	private double r;
	private final double a;
	private final double m;
	private final DoubleUnaryOperator lehmerTransform;

    public LehmerRandomGenerator(double r, double a, double m) {
    	this.a = a;
    	this.m = m;
    	this.r = r;
    	this.lehmerTransform = e -> (a * e) % m;
    }

    public double nextRandom() {
    	r = lehmerTransform.applyAsDouble(r);
    	return r; 
    }

    public DoubleStream getStream() {
    	return DoubleStream.iterate(r, lehmerTransform).map(e -> e / m).skip(1);
    }	
}
