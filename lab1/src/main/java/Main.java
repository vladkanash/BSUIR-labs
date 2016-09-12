import org.apache.commons.math3.stat.StatUtils;

public class Main {

	//temp initial values
	private static final int R = 2177;
	private static final int A = 811;
	private static final int M = 256 * 256 * 2;

	private static final int COUNT = 1000;

    public static void main(String[] args) {

    	LehmerRandomGenerator generator = new LehmerRandomGenerator(R, A, M);

    	//generator.getStream().limit(COUNT).forEach(System.out::println);

        double[] value = generator.getStream().limit(1000).toArray();

        ChartGUI.init(value);
    	System.out.println("THE END!!");
    }
}
