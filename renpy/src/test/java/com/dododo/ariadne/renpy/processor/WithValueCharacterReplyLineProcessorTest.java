package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbReply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WithValueCharacterReplyLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new WithValueCharacterReplyLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        JaxbReply expected1 = new JaxbReply("test1", "test2");
        JaxbReply expected2 = new JaxbReply("test1", "te   st2");

        Assertions.assertEquals(0, processor.accept("test1 'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("test1'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("test1   'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("test1   'te   st2'").compareTo(expected2));

        Assertions.assertNull(processor.accept("'test2'"));
        Assertions.assertNull(processor.accept("'test1' 'test2'"));
        Assertions.assertNull(processor.accept("test1"));
        Assertions.assertNull(processor.accept("te st1 'test2'"));
        Assertions.assertNull(processor.accept("0test1 'test2'"));
    }
}
