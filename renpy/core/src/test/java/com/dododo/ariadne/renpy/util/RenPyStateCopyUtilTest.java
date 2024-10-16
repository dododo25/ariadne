package com.dododo.ariadne.renpy.util;

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
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.RootComplexState;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
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
        testCopyChainState(new Marker("test"));
        testCopyChainState(new CallToState("test"));

        testCopy(new Menu(), new Option("test"), (root, child) -> ((Menu) root).addBranch((Option) child),
                root -> ((Menu) root).branchAt(0));
        testCopy(new Switch("test"), new PassState(), (root, child) -> ((Switch) root).setTrueBranch(child),
                root -> ((Switch) root).getTrueBranch());
        testCopy(new Switch("test"), new PassState(), (root, child) -> ((Switch) root).setFalseBranch(child),
                root -> ((Switch) root).getFalseBranch());

        testCopyEndPointState(new GoToPoint("test"));
        testCopyEndPointState(new EndPoint());

        testCopyComplexState(new RootComplexState());
        testCopyComplexState(new VariableGroupComplexState());
        testCopyComplexState(new LabelledGroupComplexState("test"));
        testCopyComplexState(new ComplexMenu());
        testCopyComplexState(new ComplexOption("test", null));
        testCopyComplexState(new ComplexSwitch());
        testCopyComplexState(new ComplexSwitchBranch("test", false));
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
