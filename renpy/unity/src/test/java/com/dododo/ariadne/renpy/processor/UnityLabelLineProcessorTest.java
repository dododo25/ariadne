package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UnityLabelLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new UnityLabelLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        State expected = new LabelledGroupComplexState("test");

        Assertions.assertEquals(0, processor.accept("label test").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("label   test").compareTo(expected));

        Assertions.assertNull(processor.accept("label test:"));
        Assertions.assertNull(processor.accept("labeltest"));
        Assertions.assertNull(processor.accept("labe test"));
        Assertions.assertNull(processor.accept("label 0test"));
    }
}
