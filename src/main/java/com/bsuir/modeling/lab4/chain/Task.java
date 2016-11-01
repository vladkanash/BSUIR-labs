package com.bsuir.modeling.lab4.chain;

import com.bsuir.modeling.lab4.chain.element.Machine;

/**
 * Created by vladkanash on 27.10.16.
 */
public class Task {

    private final Machine machine;

    public Task(final Machine machine) {
        this.machine = machine;
    }

    public Machine getMachine() {
        return machine;
    }
}
