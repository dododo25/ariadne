package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.SimpleJaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstJaxbFlowchartMouseStrategyTest {

    private static JaxbFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstJaxbFlowchartMouseStrategy();
    }

    @Test
    void testAcceptStateShouldDoneWell() {
        testAccept(new JaxbRootState(), new JaxbRootState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbMenu(), new JaxbRootState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbOption("test", null), new JaxbRootState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbComplexSwitch(), new JaxbSwitchBranch("test"), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new JaxbSwitchBranch("test"), new JaxbRootState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));

        testAccept(new JaxbRootState(), new JaxbPassState(), JaxbComplexState::addChild,
                (state, callback, grayStates, blackStates) -> strategy.acceptSingleState(state, callback, grayStates, blackStates));
    }

    private <T extends JaxbState, E extends JaxbState> void testAccept(T first, E second, BiConsumer<T, E> consumer, TestConsumer<E> testConsumer) {
        List<JaxbState> expectedGray = Collections.singletonList(first);
        List<JaxbState> expectedBlack = Collections.singletonList(second);

        Collection<JaxbState> grayStates = new ArrayList<>();
        Collection<JaxbState> blackStates = new ArrayList<>();

        JaxbFlowchartContract callback = new SimpleJaxbFlowchartContract() {
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
