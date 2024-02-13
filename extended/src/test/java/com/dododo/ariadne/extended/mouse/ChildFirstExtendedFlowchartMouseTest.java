package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;

class ChildFirstExtendedFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstExtendedFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new ComplexState());
        testAccept(new Label("test"));
        testAccept(new PassState());
        testAccept(new ComplexSwitch());
        testAccept(new SwitchBranch("test"));
        testAccept(new GoToPoint("test"));

        testAccept(new ComplexState(), new PassState(), ComplexState::addChild);
        testAccept(new ComplexSwitch(), new SwitchBranch("test"), ComplexState::addChild);
        testAccept(new SwitchBranch("test"), new PassState(), SwitchBranch::setNext);
    }

    private void testAccept(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }

    private <T extends State> void testAccept(T first, State second, BiConsumer<T, State> consumer) {
        Collection<State> expected = Arrays.asList(second, first);
        Collection<State> states = new ArrayList<>();

        FlowchartContract contract = new ExtendedSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        consumer.accept(first, second);
        mouse.accept(first, contract);

        Assertions.assertEquals(expected, states);
    }
}
