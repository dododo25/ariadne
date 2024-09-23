package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.SkipComplexState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LineToSkipProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new LineToSkipProcessor();
    }

    @Test
    void testAcceptShouldReturnObjectShouldDoneWell() {
        State expected = new SkipComplexState();

        Assertions.assertEquals(0, processor.accept("skip").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("skip123").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("123").compareTo(expected));
        Assertions.assertEquals(0, processor.accept("skip 123").compareTo(expected));
    }
}
