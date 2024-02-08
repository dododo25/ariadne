package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SwitchIfLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new SwitchIfLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbSwitchBranch expected = new JaxbSwitchBranch("test");

        Assertions.assertEquals(0, processor.accept("if test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("if   test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("if test :").compareTo(expected));

        Assertions.assertNull(processor.accept("if test"));
        Assertions.assertNull(processor.accept("iftest:"));
        Assertions.assertNull(processor.accept("i test:"));
    }
}
