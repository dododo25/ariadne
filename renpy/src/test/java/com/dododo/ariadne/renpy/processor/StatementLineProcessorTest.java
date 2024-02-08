package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbText;
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
        JaxbText expected1 = new JaxbText("test");
        JaxbText expected2 = new JaxbText("test = 'a'");

        Assertions.assertEquals(0, processor.accept("$ test").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("$ test = 'a'").compareTo(expected2));

        Assertions.assertNull(processor.accept("test"));
        Assertions.assertNull(processor.accept("t$est:"));
        Assertions.assertNull(processor.accept("test $"));
    }
}
