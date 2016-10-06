package com.bsuir.modeling.lab3.chain;

import java.util.Random;

/**
 * Created by vladkanash on 6.10.16.
 */
public class DummyMarkovChainElement extends MarkovChainElement {

    private final Random random = new Random();

    public DummyMarkovChainElement(String name, int initialState) {
        super(name, initialState);
    }

    public DummyMarkovChainElement(String name) {
        super(name, 0);
    }

    @Override
    public void changeState() {
        if (random.nextDouble() > 0.3) {
            this.state = 1;
        } else {
            this.state = 0;
        }
    }
}
