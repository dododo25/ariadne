package com.dododo.ariadne.jaxb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NullableStringComparatorTest {

    @Test
    void testCompareShouldDoneWell() {
        NullableStringComparator comparator = new NullableStringComparator();

        Assertions.assertEquals(0, comparator.compare(null, null));
        Assertions.assertEquals(0, comparator.compare("test", "test"));
    }

    @Test
    void testCompareWhenObjectAreNotEqualShouldDoneWell() {
        NullableStringComparator comparator = new NullableStringComparator();

        Assertions.assertNotEquals(0, comparator.compare("test", null));
        Assertions.assertNotEquals(0, comparator.compare("test", null));
        Assertions.assertNotEquals(0, comparator.compare("test", "__INVALID"));
    }
}
