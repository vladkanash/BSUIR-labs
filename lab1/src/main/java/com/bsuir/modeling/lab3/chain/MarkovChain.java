package com.bsuir.modeling.lab3.chain;

import com.bsuir.modeling.lab3.chain.element.FinalChainElement;
import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;

import java.util.*;

/**
 * Created by vladkanash on 6.10.16.
 */
public class MarkovChain {

    private final LinkedList<MarkovChainElement> elements = new LinkedList<>();
    private final static FinalChainElement finalElement = new FinalChainElement("finalElement");

    public void appendElement(MarkovChainElement element) {
        if (null != element) {
            if (elements.size() > 0) {
                element.setPrevious(this.elements.getLast());
                this.elements.getLast().setNext(element);
            }
            element.setNext(finalElement);
            this.elements.addLast(element);
        }
    }

    public void changeState() {
        Iterator<MarkovChainElement> descending = elements.descendingIterator();
        descending.forEachRemaining(MarkovChainElement::changeState);
    }

    public Map<String, Integer> getStates() {
        Map<String, Integer> states = new HashMap<>();
        elements.forEach(e -> states.put(e.getName(), e.getState()));
        return states;
    }
}
