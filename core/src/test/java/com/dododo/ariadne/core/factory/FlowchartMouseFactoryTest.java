package com.dododo.ariadne.core.factory;

import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FlowchartMouseFactoryTest {

    private static FlowchartMouseFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new FlowchartMouseFactory();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new Statement("test"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());
    }

    private void testCreateFor(State state) {
        FlowchartMouse mouse = factory.createFor(s -> Assertions.assertSame(s, state));
        state.accept(mouse);
    }
}
