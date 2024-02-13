package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;

class ChildFirstFlowchartMouseTest {

    private static FlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryState());
        testAccept(new CycleMarker("test"));
        testAccept(new CycleEntryState("test"));
        testAccept(new Text("test"));
        testAccept(new Reply(null, "test"));
        testAccept(new Menu());
        testAccept(new Option("test"));
        testAccept(new ConditionalOption("test", "test"));
        testAccept(new Switch("test"));
        testAccept(new EndPoint());

        testAccept(new Switch("test"), new EndPoint(), Switch::setTrueBranch);
        testAccept(new Switch("test"), new EndPoint(), Switch::setFalseBranch);
    }

    private void testAccept(State state) {
        mouse.accept(state, s -> Assertions.assertSame(s, state));
    }

    private <T extends State> void testAccept(T first, State second, BiConsumer<T, State> consumer) {
        Collection<State> expected = Arrays.asList(second, first);
        Collection<State> states = new ArrayList<>();

        FlowchartContract contract = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        consumer.accept(first, second);
        mouse.accept(first, contract);

        Assertions.assertEquals(expected, states);
    }
}
