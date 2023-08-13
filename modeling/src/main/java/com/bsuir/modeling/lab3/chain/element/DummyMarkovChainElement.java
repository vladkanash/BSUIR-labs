package com.bsuir.modeling.lab3.chain.element;

/**
 * Created by vladkanash on 6.10.16.
 */
public class DummyMarkovChainElement extends MarkovChainElement {

    @Override
    public void addTask() {
        this.setBlocked(true);
    }

    public DummyMarkovChainElement(String name) {
        super(name, 0);
    }

    @Override
    public void changeState() {
        this.state = 0;
    }
}
