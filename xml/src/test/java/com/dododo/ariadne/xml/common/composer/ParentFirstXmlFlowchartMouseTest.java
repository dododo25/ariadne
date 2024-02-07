package com.dododo.ariadne.xml.common.composer;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.mouse.ParentFirstXmlFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParentFirstXmlFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ParentFirstXmlFlowchartMouse();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new Text("test"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());

        testCreateFor(new ComplexState());
        testCreateFor(new ComplexSwitch());
        testCreateFor(new SwitchBranch("test"));
        testCreateFor(new Marker("test"));
        testCreateFor(new GoToPoint("test"));
        testCreateFor(new PassState());
    }

    private void testCreateFor(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
