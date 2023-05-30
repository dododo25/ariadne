package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GoToLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new GoToLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbState expected = new JaxbJumpToState("test");

        Assertions.assertEquals(0, processor.accept("goto test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("goto   test").compareTo(expected));

        Assertions.assertNull(processor.accept("gototest"));
        Assertions.assertNull(processor.accept("goto test()"));
        Assertions.assertNull(processor.accept("goto test(test_value)"));
        Assertions.assertNull(processor.accept("jum test"));
    }
}
