package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new FlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryState());
        testAccept(new Text("test"));
        testAccept(new Reply("character_test", "line_test"));
        testAccept(new Menu());
        testAccept(new Option("test"));
        testAccept(new ConditionalOption("test", "condition_test"));
        testAccept(new Switch("test"));
        testAccept(new EndPoint());
    }

    private void testAccept(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }
}
