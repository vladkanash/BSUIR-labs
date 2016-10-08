package com.bsuir.modeling.lab3.chain.element;

import java.util.Random;

/**
 * Created by vladkanash on 8.10.16.
 */
public class ProcessorChainElement extends MarkovChainElement {

    final double probability;
    Random random = new Random();

    protected final static int STATE_BUSY = 1;
    protected final static int STATE_FREE = 0;

    public ProcessorChainElement(String name, double probability) {
        super(name, STATE_FREE);

        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException
                    ("Probability value must be between 0 and 1, actual is: " + probability);
        }

        this.probability = probability;
    }

    @Override
    public void addTask() {
        this.setBlocked(true);
        this.state = STATE_BUSY;
    }

    @Override
    public void changeState() {
        final MarkovChainElement next = getNext();
        if (isBlocked() && random.nextDouble() > probability) {
            next.addTask();
            this.setBlocked(false);
            this.state = STATE_FREE;
        }
    }
}
