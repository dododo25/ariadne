package com.dododo.ariadne.xml.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class XmlFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new XmlFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new ComplexState());
        testAccept(new ComplexSwitch());
        testAccept(new ComplexSwitchBranch("test"));
        testAccept(new Marker("test"));
        testAccept(new PassState());
        testAccept(new ComplexMenu());
        testAccept(new ComplexOption("test", "condition_test"));
        testAccept(new GoToPoint("test"));
    }

    private void testAccept(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
