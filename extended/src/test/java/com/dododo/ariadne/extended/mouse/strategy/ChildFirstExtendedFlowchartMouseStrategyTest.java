package com.dododo.ariadne.extended.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstExtendedFlowchartMouseStrategyTest {

    private static ExtendedFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstExtendedFlowchartMouseStrategy();
    }

    @Test
    void testAcceptStateShouldDoneWell() {
        testAccept(new Label("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new PassState(), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new SwitchBranch("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));

        testAccept(new EntryState(), new ComplexState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
        testAccept(new EntryState(), new ComplexSwitch(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptComplexState(state, callback, grayStates, blackStates));
    }

    private <T extends State, E extends State> void testAccept(T first, E second, BiConsumer<T, E> consumer, TestConsumer<E> testConsumer) {
        List<State> expectedGray = Collections.singletonList(first);
        List<State> expectedBlack = Collections.singletonList(second);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        FlowchartContract callback = new ExtendedSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        consumer.accept(first, second);
        testConsumer.accept(second, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    interface TestConsumer<T extends State> {

        void accept(T state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);

    }
}
