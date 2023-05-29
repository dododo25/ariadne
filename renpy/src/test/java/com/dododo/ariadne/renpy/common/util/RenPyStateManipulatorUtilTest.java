package com.dododo.ariadne.renpy.common.util;

import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

class RenPyStateManipulatorUtilTest {

    @Test
    void testReplaceShouldNotThrowException() {
        testReplaceChainState(new EntryState());
        testReplaceChainState(new Statement("test"));
        testReplaceChainState(new Reply("test1", "test2"));
        testReplaceChainState(new Option("test"));
        testReplaceChainState(new ConditionalOption("test1", "test2"));

        testReplaceChainState(new LabelledGroup("test"));
        testReplaceChainState(new CallToState("test"));
        testReplaceChainState(new PassState());
        testReplaceChainState(new SwitchBranch("test"));

        process(new Switch("test"),new Statement("replaceable"),new Statement("replacement"),
                (root, state) -> ((Switch) root).setTrueBranch(state), root -> ((Switch) root).getTrueBranch());
        process(new Switch("test"),new Statement("replaceable"),new Statement("replacement"),
                (root, state) -> ((Switch) root).setFalseBranch(state), root -> ((Switch) root).getFalseBranch());

        testReplaceComplexStateChild(new ComplexState());
        testReplaceComplexStateChild(new ComplexSwitch());
    }

    @Test
    void testReplaceWhenStateHaveMultipleRootsShouldNotThrowException() {
        EntryState rootState1 = new EntryState();
        EntryState rootState2 = new EntryState();

        PassState replaceable = new PassState();
        PassState replacement = new PassState();

        rootState1.setNext(replaceable);
        rootState2.setNext(replaceable);
        RenPyStateManipulatorUtil.replace(replaceable, replacement);

        Assertions.assertEquals(replacement, rootState1.getNext());
        Assertions.assertEquals(replacement, rootState2.getNext());
        Assertions.assertTrue(Objects.deepEquals(new State[]{rootState1, rootState2}, replacement.getRoots()));
        Assertions.assertEquals(0, replaceable.getRoots().length);
    }

    private void testReplaceChainState(ChainState chainState) {
        process(chainState, new PassState(), new PassState(), (root, state) -> ((ChainState) root).setNext(state),
                root -> ((ChainState) root).getNext());
    }

    private void testReplaceComplexStateChild(ComplexState complexState) {
        process(complexState, new PassState(), new PassState(),
                (root, state) -> ((ComplexState) root).addChild(state), root -> ((ComplexState) root).childAt(0));
    }

    private void process(State rootState, State replaceable, State replacement,
                         BiConsumer<State, State> consumer, UnaryOperator<State> operator) {
        consumer.accept(rootState, replaceable);
        RenPyStateManipulatorUtil.replace(replaceable, replacement);

        Assertions.assertEquals(replacement, operator.apply(rootState));
        Assertions.assertTrue(Objects.deepEquals(new State[]{rootState}, replacement.getRoots()));
        Assertions.assertEquals(0, replaceable.getRoots().length);
    }
}
