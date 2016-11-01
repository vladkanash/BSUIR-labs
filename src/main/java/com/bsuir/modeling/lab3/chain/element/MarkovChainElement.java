package com.bsuir.modeling.lab3.chain.element;

import java.util.Random;

/**
 * Created by vladkanash on 6.10.16.
 */
public abstract class MarkovChainElement {

    protected int state;
    protected final String name;
    protected Random random;
    private MarkovChainElement next;
    private MarkovChainElement previous;
    private boolean blocked = false;


    protected MarkovChainElement(final String name, final int initialState) {
        this.state = initialState;
        this.name = name;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setNext(MarkovChainElement next) {
        this.next = next;
    }

    public void setPrevious(MarkovChainElement previous) {
        this.previous = previous;
    }

    public int getState() {
        return this.state;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public MarkovChainElement getNext() {
        return next;
    }

    public MarkovChainElement getPrevious() {
        return previous;
    }

    public abstract void addTask();

    public abstract void changeState();
}
