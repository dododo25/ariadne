package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SwitchElseLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new SwitchElseLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbSwitchFalseBranch expected = new JaxbSwitchFalseBranch();

        Assertions.assertEquals(0, processor.accept("else:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("else   :").compareTo(expected));

        Assertions.assertNull(processor.accept("else"));
        Assertions.assertNull(processor.accept("else test:"));
        Assertions.assertNull(processor.accept("els:"));
    }
}
