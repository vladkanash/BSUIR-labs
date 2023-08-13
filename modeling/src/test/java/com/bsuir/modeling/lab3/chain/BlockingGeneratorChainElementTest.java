package com.bsuir.modeling.lab3.chain;

import com.bsuir.modeling.lab3.chain.element.BlockingGeneratorChainElement;
import com.bsuir.modeling.lab3.chain.element.DummyMarkovChainElement;
import com.bsuir.modeling.lab3.chain.element.MarkovChainElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vladkanash on 8.10.16.
 */
public class BlockingGeneratorChainElementTest {

    private MarkovChainElement dummyElement;

    @Before
    public void setUp() {
        dummyElement = new DummyMarkovChainElement("Test");
    }

    @Test
    public void ChangeStateTest() {
        final BlockingGeneratorChainElement generator = new BlockingGeneratorChainElement("Test", 1.0);
        generator.setNext(dummyElement);

        for (int i = 0; i < 10; i++) {
            generator.changeState();
        }

        Assert.assertTrue("Generator should not generate any tasks if fault probability = 1", !dummyElement.isBlocked());
    }

    @Test
    public void ChangeStateTest2() {
        final BlockingGeneratorChainElement generator = new BlockingGeneratorChainElement("Test", 0.0);
        generator.setNext(dummyElement);
        generator.changeState();

        Assert.assertTrue("Generator with fault probability = 0 should produce task after every call", dummyElement.isBlocked());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void CreateGeneratorTest() {
        final BlockingGeneratorChainElement generator = new BlockingGeneratorChainElement("Test", 0.0);
        generator.addTask();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CreateGeneratorTest2() {
        final BlockingGeneratorChainElement generator = new BlockingGeneratorChainElement("Test", -1.0);
    }

}