package com.dododo.ariadne.renpy.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SingleLineCommentLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new SingleLineCommentLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        Assertions.assertNotNull(processor.accept("#test"));
        Assertions.assertNotNull(processor.accept("#test   "));
        Assertions.assertNotNull(processor.accept("#te   st"));

        Assertions.assertNull(processor.accept("test"));
    }
}
