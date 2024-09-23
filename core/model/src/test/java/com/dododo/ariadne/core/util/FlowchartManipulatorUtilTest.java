package com.dododo.ariadne.core.util;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

class FlowchartManipulatorUtilTest {

    @Test
    void testReplaceShouldNotThrowException() {
        testReplaceChainState(new EntryState());
        testReplaceChainState(new CycleMarker("test"));
        testReplaceChainState(new CycleEntryState("test"));
        testReplaceChainState(new Text("test"));
        testReplaceChainState(new Reply("character", "line"));
        testReplaceChainState(new Option("test"));
        testReplaceChainState(new ConditionalOption("test", "condition"));

        process(new Switch("test"), new Text("replaceable"), new Text("replacement"),
                (root, state) -> ((Switch) root).setTrueBranch(state), root -> ((Switch) root).getTrueBranch());
        process(new Switch("test"), new Text("replaceable"), new Text("replacement"),
                (root, state) -> ((Switch) root).setFalseBranch(state), root -> ((Switch) root).getFalseBranch());
    }

    @Test
    void testReplaceWhenStateHaveMultipleRootsShouldNotThrowException() {
        EntryState rootState1 = new EntryState();
        EntryState rootState2 = new EntryState();

        Text replaceable = new Text("replaceable");
        Text replacement = new Text("replacement");

        rootState1.setNext(replaceable);
        rootState2.setNext(replaceable);
        FlowchartManipulatorUtil.replace(replaceable, replacement);

        Assertions.assertEquals(replacement, rootState1.getNext());
        Assertions.assertEquals(replacement, rootState2.getNext());
        Assertions.assertTrue(Objects.deepEquals(new State[]{rootState1, rootState2}, replacement.getRoots()));
        Assertions.assertEquals(0, replaceable.getRoots().length);
    }

    private void testReplaceChainState(ChainState chainState) {
        process(chainState, new Text("replaceable"), new Text("replacement"),
                (root, state) -> ((ChainState) root).setNext(state), root -> ((ChainState) root).getNext());
    }

    private void process(State rootState, State replaceable, State replacement,
                         BiConsumer<State, State> consumer, UnaryOperator<State> operator) {
        consumer.accept(rootState, replaceable);
        FlowchartManipulatorUtil.replace(replaceable, replacement);

        Assertions.assertEquals(replacement, operator.apply(rootState));
        Assertions.assertTrue(Objects.deepEquals(new State[]{rootState}, replacement.getRoots()));
        Assertions.assertEquals(0, replaceable.getRoots().length);
    }
}
