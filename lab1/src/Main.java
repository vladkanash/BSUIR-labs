
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;


public class Main {

	//temp initial values
	private static final int R = 2177;
	private static final int A = 811;
	private static final int M = 256 * 256 * 2;

	private static final int COUNT = 1000;

    public static void main(String[] args) {

    	LehmerRandomGenerator generator = new LehmerRandomGenerator(R, A, M);
  //   	List<Double> values = new ArrayList<>();

  //  		IntStream.range(0, COUNT).forEach(e ->
		//    	values.add(generator.nextRandom()));

		// values.forEach(System.out::println);
    	//System.out.println(generator.getStream().limit(COUNT).distinct().count());

    	// DoubleStream stream = generator.getStream().limit(10000);
    	// List<Double> values = stream.boxed().collect(Collectors.toList());

    	// values.stream().parallel().filter(e -> Collections.frequency(values, e) > 1)
    	// .forEach(System.out::println);

    	// Map<Long, Integer> results = new HashMap<>();

    	// IntStream.iterate(0, e -> e + 1).limit(1000).parallel().forEach( e -> 
    	// 	results.put(DoubleStream.iterate(R, v -> (e * v) % M)
    	// 		.map(v -> v / M)
    	// 		.skip(1)
    	// 		.limit(COUNT)
    	// 		.distinct()
    	// 		.count(), e));

    	// results.keySet().stream().sorted().forEach(k -> 
    	// 	System.out.println(k + " = " + results.get(k)));

    	generator.getStream().limit(COUNT).forEach(System.out::println);

    	System.out.println("THE END!!");

    }
}
