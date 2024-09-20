package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
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
        State expected = new VariableGroupComplexState();

        Assertions.assertEquals(0, processor.accept("init:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("init :").compareTo(expected));

        Assertions.assertNull(processor.accept("init"));
        Assertions.assertNull(processor.accept("int:"));
        Assertions.assertNull(processor.accept(":init"));
    }
}
