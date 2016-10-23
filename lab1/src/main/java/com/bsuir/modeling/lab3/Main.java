package com.bsuir.modeling.lab3;

import com.bsuir.modeling.lab3.chain.*;
import com.bsuir.modeling.lab3.chain.element.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by vladkanash on 6.10.16.
 */
public class Main {

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input, ro, p1, p2, q;
        try {
            System.out.println("Enter steps");
            input = br.readLine();
            System.out.println("Enter ro");
            ro = br.readLine();
            System.out.println("Enter queue size");
            q = br.readLine();
            System.out.println("Enter p1");
            p1 = br.readLine();
            System.out.println("Enter p2");
            p2 = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int steps = 0;
        double iro = 0;
        double ip1 = 0;
        double ip2 = 0;
        int iq = 0;
        try {
            iro = Double.parseDouble(ro);
            iq = Integer.parseInt(q);
            ip1 = Double.parseDouble(p1);
            ip2 = Double.parseDouble(p2);
            steps = Integer.parseInt(input);
        } catch (Exception e) {
            return;
        }

        final Map<MarkovChainSnapshot, Integer> snapshots = new HashMap<>();
        final MarkovChain chain = new MarkovChain();
        double queueLength = 0;
        final QueueChainElement queue = new QueueChainElement("Q1", iq);
        final RefusingProcessorChainElement refProc = new RefusingProcessorChainElement("P1", ip1);
        final BlockingGeneratorChainElement el1 = new BlockingGeneratorChainElement("GEN1", iro);
        chain.appendElement(el1);
        chain.appendElement(queue);
        chain.appendElement(refProc);
        chain.appendElement(new ProcessorChainElement("P2", ip2));

        for (int i = 0; i < steps; i++) {
            MarkovChainSnapshot snap = new MarkovChainSnapshot(chain);
            snapshots.merge(snap, 1, (a, b) -> a + 1);
            chain.changeState();
            queueLength += queue.getState();
        }

        List<Map.Entry<MarkovChainSnapshot, Integer>> list = new ArrayList<>(snapshots.entrySet());
        Collections.sort(list, (o1, o2) -> o1.getKey().toString().compareTo(o2.getKey().toString()));

        final int stepps = steps;
        list.stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue() / (double) stepps))
                .forEach(e -> System.out.println(String.format("%s = %.3f", e.getKey(), e.getValue())));

        System.out.println("Length = " + queueLength / steps);
        System.out.println("Refused = " + (double) (el1.getTotal() - refProc.getRefusedCount()) / el1.getTotal());
        System.out.println("Time = " + (double) queue.getTotalTime() / queue.getTasksCount());
    }
}
