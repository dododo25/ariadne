package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbReply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WithoutCharacterReplyLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new WithoutCharacterReplyLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbReply expected1 = new JaxbReply(null, "test");
        JaxbReply expected2 = new JaxbReply(null, "te   st");

        Assertions.assertEquals(0, processor.accept("'test'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'   test'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'te   st'").compareTo(expected2));
        Assertions.assertEquals(0, processor.accept("'test   '").compareTo(expected1));

        Assertions.assertNull(processor.accept("test'"));
        Assertions.assertNull(processor.accept("'test"));
        Assertions.assertNull(processor.accept("invalid 'test'"));
        Assertions.assertNull(processor.accept("'invalid' 'test'"));
    }
}
