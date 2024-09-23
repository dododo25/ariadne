package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.model.CallToState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CallLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new CallLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        CallToState expected = new CallToState("test");

        Assertions.assertEquals(0, processor.accept("call test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("call   test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("call test()").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("call test(test_value)").compareTo(expected));

        Assertions.assertNull(processor.accept("calltest"));
        Assertions.assertNull(processor.accept("cal test"));
    }
}
