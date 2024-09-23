package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.extended.model.ComplexOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OptionLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new OptionLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        ComplexOption expected1 = new ComplexOption("test", null);
        ComplexOption expected2 = new ComplexOption("te   st", null);
        ComplexOption expected3 = new ComplexOption("test   ", null);

        Assertions.assertEquals(0, processor.accept("'test':").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test'   :").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'te   st':").compareTo(expected2));
        Assertions.assertEquals(0, processor.accept("'test   ':").compareTo(expected3));

        Assertions.assertNull(processor.accept("'test'"));
        Assertions.assertNull(processor.accept("test':"));
        Assertions.assertNull(processor.accept("'test:"));
    }
}
