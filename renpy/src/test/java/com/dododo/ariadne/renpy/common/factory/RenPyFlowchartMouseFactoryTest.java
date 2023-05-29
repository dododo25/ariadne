package com.dododo.ariadne.renpy.common.factory;

import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RenPyFlowchartMouseFactoryTest {

    private static FlowchartMouseFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new RenPyFlowchartMouseFactory();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new Statement("test"));
        testCreateFor(new Reply(null, "test"));
        testCreateFor(new Menu());
        testCreateFor(new Option("test"));
        testCreateFor(new ConditionalOption("test1", "test2"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());

        testCreateFor(new ComplexState());
        testCreateFor(new PassState());

        testCreateFor(new LabelledGroup("test"));
        testCreateFor(new CallToState("test"));
        testCreateFor(new JumpToPoint("test"));

        testCreateFor(new ComplexSwitch());
        testCreateFor(new SwitchBranch("test"));
    }

    private void testCreateFor(State state) {
        FlowchartMouse mouse = factory.createFor(s -> Assertions.assertSame(s, state));
        state.accept(mouse);
    }
}
