package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbOption;
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
        JaxbOption expected1 = new JaxbOption("test", null);
        JaxbOption expected2 = new JaxbOption("te   st", null);
        JaxbOption expected3 = new JaxbOption("test   ", null);

        Assertions.assertEquals(0, processor.accept("'test':").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test'   :").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'te   st':").compareTo(expected2));
        Assertions.assertEquals(0, processor.accept("'test   ':").compareTo(expected3));

        Assertions.assertNull(processor.accept("'test'"));
        Assertions.assertNull(processor.accept("test':"));
        Assertions.assertNull(processor.accept("'test:"));
    }
}
