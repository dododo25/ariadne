package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbMarker;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class JaxbFlowchartMouseTest {

    private static JaxbRootState rootState;
    private static JaxbText text;
    private static JaxbComplexSwitch complexSwitch;
    private static JaxbSwitchBranch switchBranch1;
    private static JaxbSwitchBranch switchBranch2;
    private static JaxbMarker marker;
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
        marker = new JaxbMarker("marker");
        goToState = new JaxbGoToState("goToState");
        passState = new JaxbPassState();
        endState = new JaxbEndState();

        rootState.addChild(text);
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
        List<JaxbState> expected = Arrays.asList(rootState, text, complexSwitch, switchBranch1, marker,
                passState, endState, switchBranch2, goToState);

        testAccept(expected, rootState, new ParentFirstJaxbFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<JaxbState> expected = Arrays.asList(text, marker, passState, endState, switchBranch1, goToState,
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
