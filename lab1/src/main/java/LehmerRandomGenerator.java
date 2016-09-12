import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

class LehmerRandomGenerator implements RandomGenerator {

	private double r;
	private final double m;
	private final DoubleUnaryOperator lehmerTransform;

    LehmerRandomGenerator(double r, double a, double m) {
    	this.m = m;
    	this.r = r;
    	this.lehmerTransform = e -> (a * e) % m;
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
