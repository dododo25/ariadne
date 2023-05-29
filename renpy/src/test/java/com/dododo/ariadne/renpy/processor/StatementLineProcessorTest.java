package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StatementLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new StatementLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbStatement expected1 = new JaxbStatement("test");
        JaxbStatement expected2 = new JaxbStatement("test = 'a'");

        Assertions.assertEquals(0, processor.accept("$ test").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("$ test = 'a'").compareTo(expected2));

        Assertions.assertNull(processor.accept("test"));
        Assertions.assertNull(processor.accept("t$est:"));
        Assertions.assertNull(processor.accept("test $"));
    }
}
