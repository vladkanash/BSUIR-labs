package com.bsuir.modeling.lab3.chain.element;

/**
 * Created by vladkanash on 8.10.16.
 */
public class RefusingProcessorChainElement extends ProcessorChainElement {
    public RefusingProcessorChainElement(String name, double probability) {
        super(name, probability);
    }

    private int refusedCount = 0;

    public int getRefusedCount() {
        return refusedCount;
    }

    @Override
    public void changeState() {
        if (isBlocked() && random.nextDouble() > probability) {
            if (getNext() != null && !getNext().isBlocked()) {
                getNext().addTask();
            } else {
                refusedCount++;
            }
            this.setBlocked(false);
            this.state = STATE_FREE;
        }
    }
}
