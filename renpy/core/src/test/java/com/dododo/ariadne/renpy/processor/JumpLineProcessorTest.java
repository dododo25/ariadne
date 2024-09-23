package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.extended.model.GoToPoint;
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
        GoToPoint expected = new GoToPoint("test");

        Assertions.assertEquals(0, processor.accept("jump test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("jump   test").compareTo(expected));

        Assertions.assertNull(processor.accept("jumptest"));
        Assertions.assertNull(processor.accept("jum test"));
    }
}
