package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ChildFirstJaxbFlowchartMouseTest {

    private static JaxbRootState rootState;
    private static JaxbText text;
    private static JaxbComplexSwitch complexSwitch;
    private static JaxbSwitchBranch switchBranch1;
    private static JaxbSwitchBranch switchBranch2;
    private static JaxbLabel label;
    private static JaxbGoToState goToState;
    private static JaxbPassState passState;
    private static JaxbEndState endState;

    @BeforeAll
    static void setUp() {
        rootState = new JaxbRootState();
        text = new JaxbText("text");
        complexSwitch = new JaxbComplexSwitch();
        switchBranch1 = new JaxbSwitchBranch("branch1");
        switchBranch2 = new JaxbSwitchBranch("branch2");
        label = new JaxbLabel("label");
        goToState = new JaxbGoToState("goToState");
        passState = new JaxbPassState();
        endState = new JaxbEndState();

        rootState.addChild(text);
        rootState.addChild(complexSwitch);
        complexSwitch.addChild(switchBranch1);
        complexSwitch.addChild(switchBranch2);
        switchBranch1.addChild(label);
        switchBranch1.addChild(passState);
        switchBranch1.addChild(endState);
        switchBranch2.addChild(goToState);
    }

    @Test
    void testAcceptShouldNotThrowException() {
        List<JaxbState> expected = Arrays.asList(text, label, passState, endState, goToState, switchBranch1,
                switchBranch2, complexSwitch, rootState);
        List<JaxbState> states = new ArrayList<>();

        JaxbFlowchartMouse mouse = new ChildFirstJaxbFlowchartMouse();

        mouse.accept(rootState, states::add);
        Assertions.assertEquals(expected, states);
    }
}
