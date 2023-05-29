package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new ReturnLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbEndState expected = new JaxbEndState();

        Assertions.assertEquals(0, processor.accept("return").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("return test_value").compareTo(expected));

        Assertions.assertNull(processor.accept("returntest_value"));
        Assertions.assertNull(processor.accept("retur test_value"));
    }
}
