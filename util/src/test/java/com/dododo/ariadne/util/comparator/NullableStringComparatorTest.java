package com.dododo.ariadne.util.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NullableStringComparatorTest {

    private static NullableStringComparator comparator;

    @BeforeAll
    static void setUp() {
        comparator = new NullableStringComparator();
    }

    @Test
    void testCompareShouldDoneWell() {
        Assertions.assertEquals(0, comparator.compare(null, null));
        Assertions.assertEquals(0, comparator.compare("test", "test"));
    }

    @Test
    void testCompareWhenObjectAreNotEqualShouldDoneWell() {
        Assertions.assertNotEquals(0, comparator.compare("test", null));
        Assertions.assertNotEquals(0, comparator.compare("test", null));
        Assertions.assertNotEquals(0, comparator.compare("test", "__INVALID"));
    }
}
