package com.dododo.ariadne.renpy.common.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.renpy.common.mouse.strategy.ChildFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.RenPyFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RenPyFlowchartMouseTest {

    private static ComplexState rootComplexState;
    private static PassState passState;
    private static ComplexSwitch complexSwitch;
    private static SwitchBranch switchBranch1;
    private static SwitchBranch switchBranch2;
    private static SwitchBranch switchBranch3;
    private static LabelledGroup group;
    private static CallToState callToState;
    private static JumpToPoint jumpToPoint;

    @BeforeAll
    static void setUp() {
        rootComplexState = new ComplexState();
        passState = new PassState();
        complexSwitch = new ComplexSwitch();
        switchBranch1 = new SwitchBranch("switch1");
        switchBranch2 = new SwitchBranch("switch2");
        switchBranch3 = new SwitchBranch();
        group = new LabelledGroup("group");
        callToState = new CallToState("group");
        jumpToPoint = new JumpToPoint("group");

        rootComplexState.addChild(passState);
        rootComplexState.addChild(complexSwitch);
        complexSwitch.addChild(switchBranch1);
        complexSwitch.addChild(switchBranch2);
        complexSwitch.addChild(switchBranch3);
        switchBranch1.setNext(group);
        switchBranch2.setNext(callToState);
        switchBranch3.setNext(jumpToPoint);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(rootComplexState, passState, complexSwitch, switchBranch1, group,
                switchBranch2, callToState, switchBranch3, jumpToPoint);

        testAccept(expected, rootComplexState, new ParentFirstRenPyFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(passState, group, switchBranch1, callToState, switchBranch2,
                jumpToPoint, switchBranch3, complexSwitch, rootComplexState);

        testAccept(expected, rootComplexState, new ChildFirstRenPyFlowchartMouseStrategy());
    }

    private void testAccept(List<State> expected, State rootState, RenPyFlowchartMouseStrategy strategy) {
        List<State> states = new ArrayList<>();

        RenPyFlowchartContract contract = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        RenPyFlowchartMouse mouse = new RenPyFlowchartMouse(contract, strategy);
        rootState.accept(mouse);

        Assertions.assertEquals(expected, states);
    }
}
