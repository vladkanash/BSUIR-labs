import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;

/**
 * Created by Vlad Kanash on 12.9.16.
 */
interface RandomGenerator {

    DoubleStream getStream();
}
