package com.dododo.ariadne.jaxb.comparator;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComposedState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JaxbComposedStateComparatorTest {

    private static JaxbComposedStateComparator comparator;

    @BeforeAll
    static void setUp() {
        comparator = new JaxbComposedStateComparator();
    }

    @Test
    void testCompareShouldDoneWell() {
        JaxbSwitchBranch s1 = new JaxbSwitchBranch("test");
        JaxbSwitchBranch s2 = new JaxbSwitchBranch("test");

        Assertions.assertEquals(0, comparator.compare(s1, s2));
    }

    @Test
    void testCompareWhenStatesAreNotEqualShouldDoneWell() {
        JaxbSwitchBranch s1 = new JaxbSwitchBranch("test");
        JaxbComposedState s2 = new JaxbComposedState() {
            @Override
            public void accept(JaxbFlowchartContract contract) {
                // test
            }
        };
        JaxbSwitchBranch s3 = new JaxbSwitchBranch("__INVALID");

        Assertions.assertNotEquals(0, comparator.compare(s1, s2));
        Assertions.assertNotEquals(0, comparator.compare(s1, s3));
    }
}
