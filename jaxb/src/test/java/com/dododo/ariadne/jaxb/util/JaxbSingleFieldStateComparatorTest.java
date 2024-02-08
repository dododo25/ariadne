package com.dododo.ariadne.jaxb.util;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JaxbSingleFieldStateComparatorTest {

    @Test
    void testCompareShouldDoneWell() {
        JaxbSingleFieldStateComparator comparator = new JaxbSingleFieldStateComparator();

        JaxbState s1 = new JaxbText("test");
        JaxbState s2 = new JaxbText("test");

        Assertions.assertEquals(0, comparator.compare(s1, s2));
    }
}
