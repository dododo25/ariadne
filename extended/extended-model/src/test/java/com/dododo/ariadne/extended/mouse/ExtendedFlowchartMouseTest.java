package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ExtendedFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ExtendedFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new ComplexState());
        testAccept(new ComplexSwitch());
        testAccept(new ComplexSwitchBranch("test", false));
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
