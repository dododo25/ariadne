package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.Reply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WithTextValueCharacterReplyLineProcessorTest {

    private static LineProcessor processor;

    @BeforeAll
    static void setUp() {
        processor = new WithTextValueCharacterReplyLineProcessor();
    }

    @Test
    void testAcceptShouldReturnObject() {
        Reply expected1 = new Reply("test1", "test2");
        Reply expected2 = new Reply("te   st1", "test2");
        Reply expected3 = new Reply("test1", "te   st2");

        Assertions.assertEquals(0, processor.accept("'test1' 'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1''test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'   test1' 'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'te   st1' 'test2'").compareTo(expected2));
        Assertions.assertEquals(0, processor.accept("'   test1' 'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1   ' 'test2'").compareTo(expected1));
        Assertions.assertEquals(0, processor.accept("'test1' 'te   st2'").compareTo(expected3));
        Assertions.assertEquals(0, processor.accept("'test1' 'test2   '").compareTo(expected1));

        Assertions.assertNull(processor.accept("test1' 'test2'"));
        Assertions.assertNull(processor.accept("'test1 'test2'"));
        Assertions.assertNull(processor.accept("test1 'test2'"));
        Assertions.assertNull(processor.accept("'test1' test2'"));
        Assertions.assertNull(processor.accept("'test1' 'test2"));
        Assertions.assertNull(processor.accept("'test1' test2"));
        Assertions.assertNull(processor.accept("'test1 test2'"));
    }
}
