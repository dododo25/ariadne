package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstFlowchartMouseStrategyTest {

    private static FlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstFlowchartMouseStrategy();
    }

    @Test
    void testAcceptStateShouldDoneWell() {
        testAccept(new EntryState(), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new CycleEntryState("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new CycleMarker("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new Text("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new Reply(null, "test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new Option("test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new ConditionalOption("test", "test"), new EntryState(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        
        testAccept(new Menu(), new Option("test"), Menu::addBranch,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));

        testAccept(new Switch("test"), new EntryState(), Switch::setTrueBranch,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));
        testAccept(new Switch("test"), new EntryState(), Switch::setFalseBranch,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainState(state, callback, grayStates, blackStates));

        testAccept(new EntryState(), new Menu(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptMenu(state, callback, grayStates, blackStates));
        testAccept(new EntryState(), new Switch("test"), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptSwitch(state, callback, grayStates, blackStates));
        testAccept(new EntryState(), new EndPoint(), ChainState::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptPoint(state, callback, grayStates, blackStates));
    }

    private <T extends State, E extends State> void testAccept(T first, E second, BiConsumer<T, E> consumer, TestConsumer<E> testConsumer) {
        List<State> expectedGray = Collections.singletonList(first);
        List<State> expectedBlack = Collections.singletonList(second);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
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
