package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
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
        ComplexSwitchBranch expected = new ComplexSwitchBranch("test", false);

        Assertions.assertEquals(0, processor.accept("if test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("if   test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("if test :").compareTo(expected));

        Assertions.assertNull(processor.accept("if test"));
        Assertions.assertNull(processor.accept("iftest:"));
        Assertions.assertNull(processor.accept("i test:"));
    }
}
