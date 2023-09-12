package com.dododo.ariadne.renpy.common.util;

import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

class RenPyStateCopyUtilTest {

    @Test
    void testCopyShouldReturnObject() {
        testCopyChainState(new EntryState());
        testCopyChainState(new Text("test"));
        testCopyChainState(new Reply("test1", "test2"));
        testCopyChainState(new Option("test"));
        testCopyChainState(new ConditionalOption("test1", "test2"));
        testCopyChainState(new PassState());
        testCopyChainState(new LabelledGroup("test"));
        testCopyChainState(new CallToState("test"));
        testCopyChainState(new SwitchBranch("test"));

        testCopy(new Menu(), new Option("test"), (root, child) -> ((Menu) root).addBranch((Option) child),
                root -> ((Menu) root).branchAt(0));
        testCopy(new Switch("test"), new PassState(), (root, child) -> ((Switch) root).setTrueBranch(child),
                root -> ((Switch) root).getTrueBranch());
        testCopy(new Switch("test"), new PassState(), (root, child) -> ((Switch) root).setFalseBranch(child),
                root -> ((Switch) root).getFalseBranch());

        testCopyEndPointState(new JumpToPoint("test"));
        testCopyEndPointState(new EndPoint());

        testCopyComplexState(new ComplexState());
        testCopyComplexState(new ComplexSwitch());
    }

    private void testCopyChainState(ChainState state) {
        testCopy(state, new PassState(), (root, child) -> ((ChainState) root).setNext(child),
                root -> ((ChainState) root).getNext());
    }

    private void testCopyEndPointState(State state) {
        testCopy(new EntryState(), state, (root, child) -> ((ChainState) root).setNext(child),
                root -> ((ChainState) root).getNext());
    }

    private void testCopyComplexState(ComplexState state) {
        testCopy(state, new PassState(), (root, child) -> ((ComplexState) root).addChild(child),
                root -> ((ComplexState) root).childAt(0));
    }

    private void testCopy(State rootState,
                          State childState,
                          BiConsumer<State, State> consumer,
                          UnaryOperator<State> operator) {
        consumer.accept(rootState, childState);

        State copy = RenPyStateCopyUtil.copy(rootState);

        Assertions.assertEquals(0, copy.compareTo(rootState));
        Assertions.assertEquals(0, operator.apply(copy).compareTo(childState));
    }
}
