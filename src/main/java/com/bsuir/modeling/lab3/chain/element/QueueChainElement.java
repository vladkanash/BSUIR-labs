package com.bsuir.modeling.lab3.chain.element;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * Created by vladkanash on 8.10.16.
 */
public class QueueChainElement extends MarkovChainElement {

    private final int capacity;
    private int tasksCount = 0;
    protected int totalTime = 0;

    protected Queue<Integer> queue = new ArrayDeque<>();

    public int getTasksCount() {
        return tasksCount;
    }

    public int getTotalTime() {
        int curTime = queue.stream().mapToInt(e -> e).sum();
        return totalTime + curTime;
    }

    public QueueChainElement(String name, int capacity) {
        super(name, 0);

        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be bigger then 0, actual:" + capacity);
        }

        this.capacity = capacity;
    }

    @Override
    public void addTask() {
        tasksCount++;
        if (!getNext().isBlocked()) {
            getNext().addTask();
        } else {
            if (state < capacity) {
                state++;
                queue.offer(0);
            }
            if (state == capacity) {
                setBlocked(true);
            }
        }
    }

    @Override
    public void changeState() {
        Queue<Integer> newQueue = new ArrayDeque<>();
        queue.forEach(e -> newQueue.offer(e + 1));
        queue = newQueue;
        final MarkovChainElement next = getNext();
        if (next != null && !next.isBlocked() && state > 0) {
            next.addTask();
            state--;
            totalTime += queue.poll();
            setBlocked(false);
        }

    }
}
