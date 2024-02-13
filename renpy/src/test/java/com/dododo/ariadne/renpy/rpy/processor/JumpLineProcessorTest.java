package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JumpLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new JumpLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbGoToState expected = new JaxbGoToState("test");

        Assertions.assertEquals(0, processor.accept("jump test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("jump   test").compareTo(expected));

        Assertions.assertNull(processor.accept("jumptest"));
        Assertions.assertNull(processor.accept("jum test"));
    }
}
