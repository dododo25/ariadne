package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.Text;
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
        Text expected1 = new Text("test");
        Text expected2 = new Text("test = 'a'");

        Assertions.assertEquals(0, processor.accept("$ test").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("$ test = 'a'").compareTo(expected2));

        Assertions.assertNull(processor.accept("test"));
        Assertions.assertNull(processor.accept("t$est:"));
        Assertions.assertNull(processor.accept("test $"));
    }
}
