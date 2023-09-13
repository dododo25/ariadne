package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InitLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new InitLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbState expected = new JaxbInitGroupState();

        Assertions.assertEquals(0, processor.accept("init:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("init :").compareTo(expected));

        Assertions.assertNull(processor.accept("init"));
        Assertions.assertNull(processor.accept("int:"));
        Assertions.assertNull(processor.accept(":init"));
    }
}
