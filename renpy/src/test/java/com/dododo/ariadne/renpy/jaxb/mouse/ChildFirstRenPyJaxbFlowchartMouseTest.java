package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ChildFirstRenPyJaxbFlowchartMouseTest {

    private static JaxbFlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstRenPyJaxbFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new JaxbInitGroupState());
        testAccept(new JaxbRenPyMenu());
        testAccept(new JaxbSwitchFalseBranch("test"));
        testAccept(new JaxbLabelledGroup("test"));
        testAccept(new JaxbCallToState("test"));
    }

    private void testAccept(JaxbState state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
