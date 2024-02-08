package com.dododo.ariadne.jaxb.util;

import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JaxbNoFiledStateComparatorTest {

    @Test
    void testCompareShouldDoneWell() {
        JaxbNoFiledStateComparator comparator = new JaxbNoFiledStateComparator();

        JaxbState s1 = new JaxbPassState();
        JaxbState s2 = new JaxbPassState();

        Assertions.assertEquals(0, comparator.compare(s1, s2));
    }
}
