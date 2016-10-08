package com.bsuir.modeling.lab3;

import com.bsuir.modeling.lab3.chain.*;
import com.bsuir.modeling.lab3.chain.element.BlockingGeneratorChainElement;
import com.bsuir.modeling.lab3.chain.element.ProcessorChainElement;
import com.bsuir.modeling.lab3.chain.element.QueueChainElement;
import com.bsuir.modeling.lab3.chain.element.RefusingProcessorChainElement;

import java.util.*;

/**
 * Created by vladkanash on 6.10.16.
 */
public class Main {

    private static final int STEPS = 9999;

    public static void main(String[] args) {

        final Map<MarkovChainSnapshot, Integer> snapshots = new HashMap<>();
        final MarkovChain chain = new MarkovChain();
        chain.appendElement(new BlockingGeneratorChainElement("GEN1", 0.5));
        chain.appendElement(new QueueChainElement("Q1", 2));
        chain.appendElement(new RefusingProcessorChainElement("P1", 0.5));
        chain.appendElement(new ProcessorChainElement("P2", 0.5));

        for (int i = 0; i < STEPS; i++) {
            snapshots.merge(new MarkovChainSnapshot(chain), 1, (a, b) -> a + 1);
            chain.changeState();
        }

        List<Map.Entry<MarkovChainSnapshot, Integer>> list = new ArrayList<>(snapshots.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        list.stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue() / (double) STEPS))
                .forEach(e -> System.out.println(String.format("%s = %.3f", e.getKey(), e.getValue())));
    }
}
