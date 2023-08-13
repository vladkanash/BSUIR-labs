package com.bsuir.modeling.lab1.math;

import com.bsuir.modeling.lab1.generator.RandomGenerator;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.bsuir.modeling.lab1.constants.GUIConstants;

/**
 * Created by Vlad Kanash on 15.9.16.
 */
public class MathTools {

    private MathTools() {}

    public static double mean(double[] data) {
        return StatUtils.mean(data);
    }

    public static double variance(double[] data) {
        return StatUtils.variance(data);
    }

    public static double sko(double[] data) {
        return Math.sqrt(variance(data));
    }

    public static double check(double[] data) {
        List<Pair<Double, Double>> pairs = new LinkedList<>();

        for (int i = 0; i < data.length; i+=2) {
            pairs.add(new Pair<>(data[i], data[i+1]));
        }

        long K = pairs.stream().filter(e ->
                (Math.pow(e.getFirst(), 2) + Math.pow(e.getSecond(), 2) < 1)).count();

        return K * 2.0 / GUIConstants.RANDOM_LIMIT;
    }

    //TODO optimize
    public static int period(RandomGenerator generator) {
        int i1, i2 = 0;
        double lastValue = generator.getStream().limit(GUIConstants.PERIOD_LIMIT)
                .reduce((a, b) -> b).orElse(0);

        List<Double> list = generator.getStream().limit(GUIConstants.PERIOD_LIMIT)
                .boxed().collect(Collectors.toList());

        i1 = list.indexOf(lastValue);
        if (i1 > 0) {
            list.set(i1, 0.0);
            i2 = list.indexOf(lastValue);
            return i2 - i1;
        }
        return 0;
    }

    //TODO optimize
    public static int aperiod(RandomGenerator generator) {
        List<Double> list = generator.getStream().limit(GUIConstants.PERIOD_LIMIT)
                .boxed().collect(Collectors.toList());

        int period = period(generator);
        if (period == 0) {
            return 0;
        }
        Double num = 0.0;

        for (int i = 0; i < list.size(); i++) {
            num = list.get(i);
            if (num.equals(list.get(i + period))) {
                break;
            }
        }
        return list.indexOf(num) + period;
    }

}
