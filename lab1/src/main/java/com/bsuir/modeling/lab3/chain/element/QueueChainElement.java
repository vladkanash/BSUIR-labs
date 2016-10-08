package com.bsuir.modeling.lab3.chain.element;

/**
 * Created by vladkanash on 8.10.16.
 */
public class QueueChainElement extends MarkovChainElement {

    private final int capacity;

    public QueueChainElement(String name, int capacity) {
        super(name, 0);

        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be bigger then 0, actual:" + capacity);
        }

        this.capacity = capacity;
    }

    @Override
    public void addTask() {
        if (!getNext().isBlocked()) {
            getNext().addTask();
        } else {
            if (state < capacity) {
                state++;
            }
            if (state == capacity) {
                setBlocked(true);
            }
        }
    }

    @Override
    public void changeState() {
        final MarkovChainElement next = getNext();
        if (next != null && !next.isBlocked() && state > 0) {
            next.addTask();
            state--;
            setBlocked(false);
        }

    }
}
