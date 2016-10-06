package com.bsuir.modeling.lab3.chain;

/**
 * Created by vladkanash on 6.10.16.
 */
public abstract class MarkovChainElement {

    protected int state;
    protected final String name;
    protected MarkovChainElement next;
    protected MarkovChainElement previous;

    public MarkovChainElement(final String name, final int initialState) {
        this.state = initialState;
        this.name = name;
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

    public abstract void changeState();
}
