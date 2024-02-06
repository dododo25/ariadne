package com.dododo.ariadne.core.factory;

import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FlowchartContractFactoryTest {

    private static FlowchartContractFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new FlowchartContractFactory();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new CycleMarker("test"));
        testCreateFor(new CycleEntryState("test"));
        testCreateFor(new Text("test"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());
    }

    private void testCreateFor(State state) {
        factory.process(state, s -> Assertions.assertSame(s, state));
    }
}
