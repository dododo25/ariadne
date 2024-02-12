package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ChildFirstRenPyJaxbFlowchartMouseTest {

    private static JaxbRootState rootState;
    private static JaxbText text;
    private static JaxbComplexSwitch complexSwitch;
    private static JaxbSwitchBranch switchBranch1;
    private static JaxbSwitchFalseBranch switchBranch2;
    private static JaxbLabelledGroup group;
    private static JaxbGoToState goToState;
    private static JaxbPassState passState;
    private static JaxbEndState endState;

    @BeforeAll
    static void setUp() {
        rootState = new JaxbRootState();
        text = new JaxbText("text");
        complexSwitch = new JaxbComplexSwitch();
        switchBranch1 = new JaxbSwitchBranch("branch1");
        switchBranch2 = new JaxbSwitchFalseBranch("branch2");
        group = new JaxbLabelledGroup("group");
        goToState = new JaxbGoToState("goToState");
        passState = new JaxbPassState();
        endState = new JaxbEndState();

        rootState.addChild(text);
        rootState.addChild(complexSwitch);
        complexSwitch.addChild(switchBranch1);
        complexSwitch.addChild(switchBranch2);
        switchBranch1.addChild(group);
        switchBranch1.addChild(passState);
        switchBranch1.addChild(endState);
        switchBranch2.addChild(goToState);
    }

    @Test
    void testAcceptShouldNotThrowException() {
        Collection<JaxbState> expected = Arrays.asList(text, group, passState, endState, goToState, switchBranch1,
                switchBranch2, complexSwitch, rootState);
        List<JaxbState> states = new ArrayList<>();

        JaxbFlowchartMouse mouse = new ChildFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, states::add);
        Assertions.assertEquals(expected, states);
    }
}
