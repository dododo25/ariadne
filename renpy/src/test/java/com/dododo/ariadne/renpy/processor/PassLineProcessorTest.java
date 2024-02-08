package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbPassState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PassLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new PassLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbPassState expected = new JaxbPassState();

        Assertions.assertEquals(0, processor.accept("pass").compareTo(expected));
        Assertions.assertNull(processor.accept("test"));
    }
}
