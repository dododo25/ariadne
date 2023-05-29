package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbStatement;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class JaxbFlowchartMouseTest {

    private static JaxbGroupState rootState;
    private static JaxbStatement statement;
    private static JaxbComplexSwitch complexSwitch;
    private static JaxbSwitchBranch switchBranch1;
    private static JaxbSwitchFalseBranch switchBranch2;
    private static JaxbLabelledGroup marker;
    private static JaxbJumpToState goToState;
    private static JaxbPassState passState;
    private static JaxbEndState endState;

    @BeforeAll
    static void setUp() {
        rootState = new JaxbGroupState();
        statement = new JaxbStatement("statement");
        complexSwitch = new JaxbComplexSwitch();
        switchBranch1 = new JaxbSwitchBranch("branch1");
        switchBranch2 = new JaxbSwitchFalseBranch("branch2");
        marker = new JaxbLabelledGroup("group");
        goToState = new JaxbJumpToState("jumpToState");
        passState = new JaxbPassState();
        endState = new JaxbEndState();

        rootState.addChild(statement);
        rootState.addChild(complexSwitch);
        complexSwitch.addChild(switchBranch1);
        complexSwitch.addChild(switchBranch2);
        switchBranch1.addChild(marker);
        switchBranch1.addChild(passState);
        switchBranch1.addChild(endState);
        switchBranch2.addChild(goToState);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<JaxbState> expected = Arrays.asList(rootState, statement, complexSwitch, switchBranch1, marker,
                passState, endState, switchBranch2, goToState);

        testAccept(expected, rootState, new ParentFirstJaxbFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<JaxbState> expected = Arrays.asList(statement, marker, passState, endState, switchBranch1, goToState,
                switchBranch2, complexSwitch, rootState);

        testAccept(expected, rootState, new ChildFirstJaxbFlowchartMouseStrategy());
    }

    private void testAccept(List<JaxbState> expected, JaxbState rootState, JaxbFlowchartMouseStrategy strategy) {
        List<JaxbState> states = new ArrayList<>();

        JaxbFlowchartContract contract = new JaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                states.add(state);
            }
        };

        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(contract, strategy);
        rootState.accept(mouse);

        Assertions.assertEquals(expected, states);
    }
}
