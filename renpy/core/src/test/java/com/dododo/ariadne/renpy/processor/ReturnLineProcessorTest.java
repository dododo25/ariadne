package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.EndPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new ReturnLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        EndPoint expected = new EndPoint();

        Assertions.assertEquals(0, processor.accept("return").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("return test_value").compareTo(expected));

        Assertions.assertNull(processor.accept("returntest_value"));
        Assertions.assertNull(processor.accept("retur test_value"));
    }
}
