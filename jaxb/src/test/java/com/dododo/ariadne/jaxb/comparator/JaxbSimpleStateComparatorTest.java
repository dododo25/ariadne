package com.dododo.ariadne.jaxb.comparator;

import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JaxbSimpleStateComparatorTest {

    private static JaxbSimpleStateComparator comparator;

    @BeforeAll
    static void setUp() {
        comparator = new JaxbSimpleStateComparator();
    }

    @Test
    void testCompareShouldDoneWell() {
        JaxbState s1 = new JaxbText("test");
        JaxbState s2 = new JaxbText("test");

        Assertions.assertEquals(0, comparator.compare(s1, s2));
    }

    @Test
    void testCompareWhenStatesAreNotEqualShouldDoneWell() {
        JaxbState s1 = new JaxbText("test");
        JaxbState s2 = new JaxbPassState();
        JaxbState s3 = new JaxbText("__INVALID");

        Assertions.assertNotEquals(0, comparator.compare(s1, s2));
        Assertions.assertNotEquals(0, comparator.compare(s1, s3));
    }
}
