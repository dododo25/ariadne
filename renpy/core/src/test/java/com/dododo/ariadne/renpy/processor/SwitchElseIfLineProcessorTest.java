package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SwitchElseIfLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new SwitchElseIfLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        ComplexSwitchBranch expected = new ComplexSwitchBranch("test", true);

        Assertions.assertEquals(0, processor.accept("elif test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("elif   test:").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("elif test :").compareTo(expected));

        Assertions.assertNull(processor.accept("elif test"));
        Assertions.assertNull(processor.accept("eliftest:"));
        Assertions.assertNull(processor.accept("eli test:"));
    }
}
