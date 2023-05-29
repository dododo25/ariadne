package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
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
        JaxbMenu expected = new JaxbMenu();

        Assertions.assertEquals(0, processor.accept("menu:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("menu   :").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("menu():").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("menu(test_value):").compareTo(expected));

        Assertions.assertNull(processor.accept("menu"));
        Assertions.assertNull(processor.accept("men:"));
        Assertions.assertNull(processor.accept("menu(:"));
    }
}
