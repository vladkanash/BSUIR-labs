package com.bsuir.modeling.lab3.chain;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by vladkanash on 6.10.16.
 */
public class MarkovChainSnapshot {

    private static final Map<List<Integer>, String> stateNumbers = new HashMap<>();
    static {
        stateNumbers.put(Arrays.asList(0, 0, 0, 0), "P0");
        stateNumbers.put(Arrays.asList(0, 0, 0, 1), "P1");
        stateNumbers.put(Arrays.asList(0, 0, 1, 0), "P2");
        stateNumbers.put(Arrays.asList(0, 0, 1, 1), "P3");
        stateNumbers.put(Arrays.asList(0, 1, 1, 0), "P4");
        stateNumbers.put(Arrays.asList(0, 1, 1, 1), "P5");
        stateNumbers.put(Arrays.asList(0, 2, 1, 0), "P6");
        stateNumbers.put(Arrays.asList(0, 2, 1, 1), "P7");
        stateNumbers.put(Arrays.asList(1, 2, 1, 0), "P8");
        stateNumbers.put(Arrays.asList(1, 2, 1, 1), "P9");

    }

    private LinkedHashMap<String, Integer> snapshot = new LinkedHashMap<>();

    public MarkovChainSnapshot(MarkovChain chain) {
        if (null != chain) {
            snapshot.putAll(chain.getStates());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) {
            return false;
        }
        if (!(other instanceof MarkovChainSnapshot)) {
            return false;
        }
        MarkovChainSnapshot otherSnapshot = (MarkovChainSnapshot) other;
        if (this.snapshot.size() != otherSnapshot.snapshot.size()) {
            return false;
        }
        for (Entry<String, Integer> e: this.snapshot.entrySet()) {
            if (!otherSnapshot.snapshot.containsKey(e.getKey())) {
                return false;
            }
            if (!otherSnapshot.snapshot.get(e.getKey()).equals(e.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Entry e: this.snapshot.entrySet()) {
            hashCode += e.getKey().hashCode() + e.getValue().hashCode() * 31;
        }
        return hashCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        List<Integer> values = new ArrayList<>(snapshot.values());
        String state = stateNumbers.containsKey(values) ? stateNumbers.get(values) : "ERR";
        builder.append(state);
        builder.append(") ");
        this.snapshot.forEach((k, v) -> {
            builder.append(v);
            builder.append("-");
        });
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

}
