package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParentFirstExtendedFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ParentFirstExtendedFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryState());
        testAccept(new CycleMarker("test"));
        testAccept(new CycleEntryState("test"));
        testAccept(new Text("test"));
        testAccept(new Switch("test"));
        testAccept(new EndPoint());
    }

    private void testAccept(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
