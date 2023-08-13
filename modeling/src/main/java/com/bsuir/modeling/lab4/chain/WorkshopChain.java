package com.bsuir.modeling.lab4.chain;

import com.bsuir.modeling.lab3.chain.Chain;
import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;
import com.bsuir.modeling.lab4.chain.element.Machine;
import com.bsuir.modeling.lab4.chain.element.MachineQueue;
import com.bsuir.modeling.lab4.chain.element.Worker;

import java.util.*;

/**
 * Created by vladkanash on 27.10.16.
 */
public class WorkshopChain implements Chain {

    private final List<Machine> machines = new ArrayList<>();
    private final List<Worker> workers = new ArrayList<>();
    private final MachineQueue queue;

    public WorkshopChain(final int machinesCount,
                         final double machineIntensity,
                         final int workersCount,
                         final double workerIntensity) {

        queue = new MachineQueue("Q");

        for (int i = 0; i < workersCount; i++) {
            final Worker worker = new Worker("W" + i, workerIntensity);
            workers.add(worker);
            queue.addWorker(worker);
        }

        for (int i = 0; i < machinesCount; i++) {
            Machine machine = new Machine("M" + i, machineIntensity);
            machine.setNext(queue);
            machines.add(machine);
        }
    }

    public void changeState() {
        workers.forEach(MarkovChainElement::changeState);
        queue.changeState();
        machines.forEach(MarkovChainElement::changeState);
    }

    @Override
    public Integer getState() {
        return (int) machines.stream().filter(MarkovChainElement::isBlocked).count();
    }

    public int getWorkers() {
        return (int) workers.stream().filter(MarkovChainElement::isBlocked).count();
    }

    public int getTotalTasks() {
        return machines.stream().map(Machine::getTotalTasks).mapToInt(e -> e).sum();
    }
}
