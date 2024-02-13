package com.dododo.ariadne.renpy.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.contract.SimpleRenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstRenPyJaxbFlowchartMouseStrategyTest {

    private static JaxbFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstRenPyJaxbFlowchartMouseStrategy();
    }

    @Test
    void testAcceptStateShouldDoneWell() {
        testAccept(new JaxbRootState(), new JaxbInitGroupState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbRootState(), new JaxbRenPyMenu(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbRootState(), new JaxbSwitchFalseBranch("test"), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbRootState(), new JaxbLabelledGroup("test"), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
    }

    private <T extends JaxbState, E extends JaxbState> void testAccept(T first, E second, BiConsumer<T, E> consumer, TestConsumer<E> testConsumer) {
        List<JaxbState> expectedGray = Collections.singletonList(first);
        List<JaxbState> expectedBlack = Collections.singletonList(second);

        Collection<JaxbState> grayStates = new ArrayList<>();
        Collection<JaxbState> blackStates = new ArrayList<>();

        JaxbFlowchartContract callback = new SimpleRenPyJaxbFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                // test
            }
        };

        consumer.accept(first, second);
        testConsumer.accept(second, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    interface TestConsumer<T extends JaxbState> {

        void accept(T state, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates);

    }
}
