package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.extended.model.ComplexMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MenuLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new MenuLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        ComplexMenu e1 = new ComplexMenu();

        Assertions.assertEquals(0, processor.accept("menu:").compareTo(e1));
        Assertions.assertEquals(0, processor.accept("menu   :").compareTo(e1));

        Assertions.assertNull(processor.accept("menu"));
        Assertions.assertNull(processor.accept("men:"));
        Assertions.assertNull(processor.accept("menu(:"));
    }
}
