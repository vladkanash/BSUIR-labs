package com.bsuir.modeling.lab3.chain.element;

/**
 * Created by vladkanash on 8.10.16.
 */
public class RefusingProcessorChainElement extends ProcessorChainElement {
    public RefusingProcessorChainElement(String name, double probability) {
        super(name, probability);
    }

    @Override
    public void changeState() {
        if (isBlocked() && random.nextDouble() > probability) {
            if (getNext() != null && !getNext().isBlocked()) {
                getNext().addTask();
            }
            this.setBlocked(false);
            this.state = STATE_FREE;
        }
    }
}
