package com.dododo.ariadne.renpy.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InvalidLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new InvalidLineProcessor("^[0-9]+$");
        processor.setNext(new LineToSkipProcessor());
    }

    @Test
    void testIsValidShouldReturnBoolean() {
        Assertions.assertNull(processor.accept("123"));

        Assertions.assertNotNull(processor.accept("test"));
        Assertions.assertNotNull(processor.accept("test123"));
        Assertions.assertNotNull(processor.accept(""));
    }
}
