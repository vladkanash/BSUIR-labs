package com.bsuir.modeling.lab3.chain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by vladkanash on 6.10.16.
 */
public class MarkovChain {

    private final LinkedList<MarkovChainElement> elements = new LinkedList<>();

    public void appendElement(MarkovChainElement element) {
        if (null != element) {
            if (elements.size() > 0) {
                element.setPrevious(this.elements.getLast());
                this.elements.getLast().setNext(element);
            }
            this.elements.addLast(element);
        }
    }

    public void changeState() {
        elements.forEach(MarkovChainElement::changeState);
    }

    public Map<String, Integer> getStates() {
        Map<String, Integer> states = new HashMap<>();
        elements.forEach(e -> states.put(e.getName(), e.getState()));
        return states;
    }
}
