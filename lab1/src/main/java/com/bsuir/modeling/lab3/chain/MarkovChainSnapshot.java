package com.bsuir.modeling.lab3.chain;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Created by vladkanash on 6.10.16.
 */
public class MarkovChainSnapshot extends LinkedHashMap<String, Integer> {

    public MarkovChainSnapshot(MarkovChain chain) {
        super();
        if (null != chain) {
            this.putAll(chain.getStates());
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
        if (this.size() != otherSnapshot.size()) {
            return false;
        }
        for (Entry<String, Integer> e: this.entrySet()) {
            if (!otherSnapshot.containsKey(e.getKey())) {
                return false;
            }
            if (!otherSnapshot.get(e.getKey()).equals(e.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Entry e: this.entrySet()) {
            hashCode += e.getKey().hashCode() + e.getValue().hashCode() * 31;
        }
        return hashCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        this.forEach((k, v) -> {
            builder.append(v);
            builder.append("=");
        });
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

}
