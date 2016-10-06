package com.bsuir.modeling.lab3;

import com.bsuir.modeling.lab3.chain.DummyMarkovChainElement;
import com.bsuir.modeling.lab3.chain.MarkovChain;
import com.bsuir.modeling.lab3.chain.MarkovChainSnapshot;

import java.util.*;

/**
 * Created by vladkanash on 6.10.16.
 */
public class Main {

    public static void main(String[] args) {

        final Map<MarkovChainSnapshot, Integer> snapshots = new HashMap<>();
        final MarkovChain chain = new MarkovChain();
        chain.appendElement(new DummyMarkovChainElement("E1"));
        chain.appendElement(new DummyMarkovChainElement("N1"));
        chain.appendElement(new DummyMarkovChainElement("N2", 3));
        chain.appendElement(new DummyMarkovChainElement("O15", 2));

        for (int i = 0; i < 9999; i++) {
            chain.changeState();
            snapshots.merge(new MarkovChainSnapshot(chain), 1, (a, b) -> a + 1);
        }

        List<Map.Entry<MarkovChainSnapshot, Integer>> list = new ArrayList<>(snapshots.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        list.forEach(System.out::println);
    }
}
