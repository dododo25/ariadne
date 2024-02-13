package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ChildFirstJaxbFlowchartMouseTest {

    private static JaxbFlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstJaxbFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell0() {
        testAccept(new JaxbRootState());
        testAccept(new JaxbMenu());
        testAccept(new JaxbOption("test", null));
        testAccept(new JaxbComplexSwitch());
        testAccept(new JaxbSwitchBranch("test"));

        testAccept(new JaxbText("test"));
        testAccept(new JaxbReply(null, "test"));
        testAccept(new JaxbLabel("test"));
        testAccept(new JaxbGoToState("test"));
        testAccept(new JaxbPassState());
        testAccept(new JaxbEndState());
    }

    private void testAccept(JaxbState state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
