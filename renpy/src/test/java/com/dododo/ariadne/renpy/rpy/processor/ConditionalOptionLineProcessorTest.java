package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConditionalOptionLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new ConditionalOptionLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbOption expected1 = new JaxbOption("test1", "test2");
        JaxbOption expected2 = new JaxbOption("te   st1", "test2");
        JaxbOption expected3 = new JaxbOption("test1   ", "test2");
        JaxbOption expected4 = new JaxbOption("test1", null);

        Assertions.assertEquals(0, processor.accept("'test1' if test2:").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1'if test2:").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1'   if test2:").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1' if   test2:").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1' if test2  :").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'te   st1' if test2:").compareTo(expected2));
        Assertions.assertEquals(0, processor.accept("'test1   ' if test2:").compareTo(expected3));
        Assertions.assertEquals(0, processor.accept("'test1' if True:").compareTo(expected4));

        Assertions.assertNull(processor.accept("'test1':"));
        Assertions.assertNull(processor.accept("'test1' if:"));
        Assertions.assertNull(processor.accept("'test1' iftest2:"));
        Assertions.assertNull(processor.accept("'test1' if test2"));
        Assertions.assertNull(processor.accept("test1' if test2:"));
        Assertions.assertNull(processor.accept("'test1 if test2:"));
    }
}
