package com.bsuir.modeling.lab4;

import com.bsuir.modeling.lab4.chain.WorkshopChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladkanash on 26.10.16.
 */
public class Main {

    private final static int STEPS = 1000000;

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String p, q, machines, workers;

        try {
            System.out.println("Enter lambda");
            p = br.readLine();
            System.out.println("Enter machines count");
            machines = br.readLine();
            System.out.println("Enter nu");
            q = br.readLine();
            System.out.println("Enter workers count");
            workers = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int machinesInt, workersInt;
        double pD, qD;
        try {
            pD = Double.parseDouble(p);
            qD = Double.parseDouble(q);
            machinesInt = Integer.parseInt(machines);
            workersInt = Integer.parseInt(workers);

        } catch (Exception e) {
            return;
        }

        final WorkshopChain chain = new WorkshopChain(machinesInt, 60 / pD, workersInt, 60 / qD);
        final Map<Integer, Integer> snapshots = new HashMap<>();

        int totalLength = 0;
        int totalWorkers = 0;

        for (int i = 0; i < STEPS; i++) {
            chain.changeState();
            snapshots.merge(chain.getState(), 1, (a, b) -> a + 1);
            totalLength += chain.getState();
            totalWorkers += chain.getWorkers();
        }

//        snapshots.entrySet().forEach(e -> System.out.println(e.getKey() + " = " +
//                String.format("%.5f", e.getValue() / (double) STEPS)));

        System.out.println("Queue length: " + totalLength / (double) STEPS);
        System.out.println("Average workers: " + totalWorkers / (double) STEPS);
        System.out.println("Tasks: " + chain.getTotalTasks() / (double) STEPS * 60);
    }
}
