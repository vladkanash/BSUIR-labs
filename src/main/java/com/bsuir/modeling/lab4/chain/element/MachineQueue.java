package com.bsuir.modeling.lab4.chain.element;

import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;
import com.bsuir.modeling.lab4.chain.Task;

import java.util.*;

/**
 * Created by vladkanash on 27.10.16.
 */
public class MachineQueue extends MarkovChainElement {

    public MachineQueue(String name) {
        super(name, 0);
    }

    @Override
    public void addTask() {

    }

    private final Queue<Task> tasks = new LinkedList<>();
    private final List<Worker> workers = new ArrayList<>();

    public void addTask(final Task task) {
        tasks.offer(task);
        processTask();
    }

    public void addWorker(final Worker worker) {
        this.workers.add(worker);
    }

    @Override
    public void changeState() {
        if (!tasks.isEmpty()) {
            processTask();
        }
    }

    private void processTask() {
        workers.stream().filter(e -> !e.isBlocked())
                .findAny()
                .ifPresent(w -> w.addTask(tasks.poll()));
    }

    public int getState() {
        return tasks.size();
    }
}
