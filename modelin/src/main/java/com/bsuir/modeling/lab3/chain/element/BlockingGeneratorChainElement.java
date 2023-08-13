package com.bsuir.modeling.lab3.chain.element;

import java.util.Random;

/**
 * Created by vladkanash on 6.10.16.
 */
public class BlockingGeneratorChainElement extends MarkovChainElement {

    private final static int STATE_BLOCKED = 1;
    private final static int STATE_NOT_BLOCKED = 0;

    private final double probability;

    private boolean storedValue = false;
    private int totalTasks = 0;

    public int getTotal() {
        return this.totalTasks;
    }

    public BlockingGeneratorChainElement(String name, double probability) {
        super(name, STATE_NOT_BLOCKED);

        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException
                    ("Probability value must be between 0 and 1, actual is: " + probability);
        }

        this.probability = probability;
    }

    @Override
    public void addTask() {
        throw new UnsupportedOperationException("You cannot add tasks to generator element");
    }

    @Override
    public void changeState() {
        final MarkovChainElement next = getNext();
        final boolean generate = random.nextDouble() > probability;
        if (next == null) {
            return;
        }
        if (!storedValue && generate) {
            if (!next.isBlocked()) {
                next.addTask();
                totalTasks++;
            } else {
                storedValue = true;
            }
        }
        if (!next.isBlocked() && storedValue) {
            next.addTask();

            storedValue = generate;
        }
        state = storedValue ? STATE_BLOCKED : STATE_NOT_BLOCKED;
    }
}
