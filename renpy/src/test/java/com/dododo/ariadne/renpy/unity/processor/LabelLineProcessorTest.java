package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LabelLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new LabelLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbLabelledGroup expected = new JaxbLabelledGroup("test");

        Assertions.assertEquals(0, processor.accept("label test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("label   test").compareTo(expected));

        Assertions.assertNull(processor.accept("label test:"));
        Assertions.assertNull(processor.accept("labeltest"));
        Assertions.assertNull(processor.accept("labe test"));
        Assertions.assertNull(processor.accept("label 0test"));
    }
}
