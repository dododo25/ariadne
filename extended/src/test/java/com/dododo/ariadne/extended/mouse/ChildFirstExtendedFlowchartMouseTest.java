package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ChildFirstExtendedFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstExtendedFlowchartMouse();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new Text("test"));
        testCreateFor(new Reply(null, "test"));
        testCreateFor(new Menu());
        testCreateFor(new Option("test"));
        testCreateFor(new ConditionalOption("test1", "test2"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());

        testCreateFor(new ComplexState());
        testCreateFor(new PassState());

        testCreateFor(new Label("test"));
        testCreateFor(new GoToPoint("test"));

        testCreateFor(new ComplexSwitch());
        testCreateFor(new SwitchBranch("test"));
    }

    private void testCreateFor(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
