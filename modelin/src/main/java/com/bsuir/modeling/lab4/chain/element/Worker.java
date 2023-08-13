package com.bsuir.modeling.lab4.chain.element;

import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;
import com.bsuir.modeling.lab4.chain.Task;
import org.apache.commons.math3.distribution.PoissonDistribution;

/**
 * Created by vladkanash on 27.10.16.
 */
public class Worker extends MarkovChainElement {

    private final static int STATE_READY = 0;
    private final static int STATE_BUSY = 1;
    private PoissonDistribution poissonDistribution;

    private int steps = 0;
    private Task task;

    public Worker(final String name, final double intensity) {
        super(name, STATE_READY);

        if (intensity > 0) {
            poissonDistribution = new PoissonDistribution(intensity);
        }
    }

    @Override
    public void addTask() {
        throw new UnsupportedOperationException("Use addTask(Task) for this worker");
    }

    @Override
    public void changeState() {
        if (isBlocked()) {
            if (steps <= 0) {
                state = STATE_READY;
                setBlocked(false);
                task.getMachine().setBlocked(false);
            } else {
                steps--;
            }
        }
    }

    public void addTask(Task task) {
        if (!isBlocked()) {
            steps = poissonDistribution.sample();
            //System.out.println(this.name + "  => " + steps);
            this.task = task;
            this.setBlocked(true);
            state = STATE_BUSY;
        }
    }
}
