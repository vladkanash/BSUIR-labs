package com.bsuir.modeling.lab3.chain;

/**
 * Created by vladkanash on 6.10.16.
 */
public class GeneratorChainElement extends MarkovChainElement {

    public GeneratorChainElement(String name, int initialState) {
        super(name, initialState);
    }

    @Override
    public void changeState() {
    }
}
