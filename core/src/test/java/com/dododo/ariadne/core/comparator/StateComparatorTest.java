package com.dododo.ariadne.core.comparator;

import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StateComparatorTest {

    private static StateComparator comparator;

    @BeforeAll
    static void setUp() {
        comparator = new StateComparator(new FlowchartContractFactory());
    }

    @Test
    void testIsEqualShouldReturnBoolean() {
        Assertions.assertEquals(0, comparator.compare(new EntryState(), new EntryState()));
        Assertions.assertEquals(0, comparator.compare(new EndPoint(), new EndPoint()));
        Assertions.assertEquals(0, comparator.compare(new CycleMarker("test"), new CycleMarker("test")));
        Assertions.assertEquals(0, comparator.compare(new CycleEntryState("test"), new CycleEntryState("test")));
        Assertions.assertEquals(0, comparator.compare(new Text("test"), new Text("test")));

        Assertions.assertEquals(0, comparator.compare(new Switch("test"), new Switch("test")));

        Assertions.assertNotEquals(0, comparator.compare(new CycleMarker("test"), new CycleMarker("__INVALID")));
        Assertions.assertNotEquals(0, comparator.compare(new CycleEntryState("test"),
                new CycleEntryState("__INVALID")));
        Assertions.assertNotEquals(0, comparator.compare(new Text("test"), new Text("__INVALID")));
        Assertions.assertNotEquals(0, comparator.compare(new Switch("test"), new Switch("__INVALID")));
    }

    @Test
    void testIsEqualWhenStateIsComplexShouldReturnBoolean() {
        EntryState entryState1 = new EntryState();
        EntryState entryState2 = new EntryState();

        Switch aSwitch1 = new Switch("test");
        Switch aSwitch2 = new Switch("test");

        Text text1 = new Text("test");
        Text text2 = new Text("test");

        EndPoint endPoint = new EndPoint();

        entryState1.setNext(aSwitch1);
        aSwitch1.setTrueBranch(text1);
        text1.setNext(entryState1);
        aSwitch1.setFalseBranch(endPoint);

        entryState2.setNext(aSwitch2);
        aSwitch2.setTrueBranch(text2);
        text2.setNext(entryState2);
        aSwitch2.setFalseBranch(endPoint);

        Assertions.assertEquals(0, comparator.compare(entryState1, entryState2));
    }

    @Test
    void testIsEqualWhenStateIsComplexAndNodesAreNotEqualShouldReturnBoolean() {
        EntryState entryState1 = new EntryState();
        EntryState entryState2 = new EntryState();

        Switch aSwitch1 = new Switch("test");
        Switch aSwitch2 = new Switch("test");

        Text text1 = new Text("test");

        EndPoint endPoint = new EndPoint();

        entryState1.setNext(aSwitch1);
        aSwitch1.setTrueBranch(text1);
        text1.setNext(entryState1);
        aSwitch1.setFalseBranch(endPoint);

        entryState2.setNext(aSwitch2);
        aSwitch2.setTrueBranch(entryState2);
        aSwitch2.setFalseBranch(endPoint);

        Assertions.assertNotEquals(0, comparator.compare(entryState1, entryState2));
    }

    @Test
    void testIsEqualWhenStateIsComplexAndEdgeAreNotEqualShouldReturnBoolean() {
        EntryState entryState1 = new EntryState();
        EntryState entryState2 = new EntryState();

        Switch aSwitch1 = new Switch("test");
        Switch aSwitch2 = new Switch("test");

        Text text1 = new Text("test");
        Text text2 = new Text("test");

        EndPoint endPoint = new EndPoint();

        entryState1.setNext(aSwitch1);
        aSwitch1.setTrueBranch(text1);
        text1.setNext(entryState1);
        aSwitch1.setFalseBranch(endPoint);

        entryState2.setNext(aSwitch2);
        aSwitch2.setTrueBranch(endPoint);
        aSwitch2.setFalseBranch(text2);
        text2.setNext(entryState2);

        Assertions.assertNotEquals(0, comparator.compare(entryState1, entryState2));
    }
}
