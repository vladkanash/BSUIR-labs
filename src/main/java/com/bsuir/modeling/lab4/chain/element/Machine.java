package com.bsuir.modeling.lab4.chain.element;

import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;
import com.bsuir.modeling.lab4.chain.Task;
import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.Random;

/**
 * Created by vladkanash on 26.10.16.
 */
public class Machine extends MarkovChainElement {

    private final static int STATE_WORKING = 0;
    private final static int STATE_BROKEN = 1;
    private PoissonDistribution poissonDistribution;

    private int steps = 0;
    private int totalTasks = 0;

    public Machine(String name, double intensity) {
        super(name, STATE_WORKING);

        if (intensity > 0) {
            this.poissonDistribution = new PoissonDistribution(intensity);
//            steps = new Random().nextInt((int) intensity);
                steps = poissonDistribution.sample();
        }
    }

    @Override
    public void addTask() {
        throw new UnsupportedOperationException("You cannot add tasks to Machine");
    }

    @Override
    public void changeState() {
        if (!isBlocked()) {
            if (steps <= 0) {
                state = STATE_BROKEN;
                setBlocked(true);
                totalTasks++;
                ((MachineQueue) getNext()).addTask(new Task(this));
                steps = poissonDistribution.sample();
            } else {
                steps--;
            }
        }
    }

    public int getTotalTasks() {
        return totalTasks;
    }
}
