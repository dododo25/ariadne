package com.dododo.ariadne.xml.common.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.mouse.strategy.ChildFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.mouse.strategy.XmlFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class XmlFlowchartMouseTest {

    private static ComplexState rootComplexState;
    private static ComplexSwitch complexSwitch;
    private static SwitchBranch switchTrueBranchComplexState;
    private static SwitchBranch switchFalseBranchComplexState;

    private static Text text1;
    private static Text text2;

    @BeforeAll
    static void setUp() {
        rootComplexState = new ComplexState();
        complexSwitch = new ComplexSwitch();
        switchTrueBranchComplexState = new SwitchBranch("test");
        switchFalseBranchComplexState = new SwitchBranch("test");

        text1 = new Text("test1");
        text2 = new Text("test2");

        rootComplexState.addChild(complexSwitch);
        complexSwitch.addChild(switchTrueBranchComplexState);
        complexSwitch.addChild(switchFalseBranchComplexState);
        switchTrueBranchComplexState.setNext(text1);
        switchFalseBranchComplexState.setNext(text2);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(rootComplexState, complexSwitch, switchTrueBranchComplexState,
                text1, switchFalseBranchComplexState, text2);

        testAccept(expected, rootComplexState, new ParentFirstXmlFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(text1, switchTrueBranchComplexState, text2,
                switchFalseBranchComplexState, complexSwitch, rootComplexState);

        testAccept(expected, rootComplexState, new ChildFirstXmlFlowchartMouseStrategy());
    }

    private void testAccept(List<State> expected, State rootState, XmlFlowchartMouseStrategy strategy) {
        List<State> states = new ArrayList<>();

        XmlFlowchartContract contract = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(contract, strategy);
        rootState.accept(mouse);

        Assertions.assertEquals(expected, states);
    }
}
