package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FlowchartMouseTest {

    private static EntryState entryState;
    private static Text text1;
    private static Text text2;
    private static Reply reply;
    private static Menu menu;
    private static Option option1;
    private static ConditionalOption option2;
    private static Switch aSwitch;
    private static EndPoint endPoint1;
    private static EndPoint endPoint2;

    @BeforeAll
    static void setUp() {
        entryState = new EntryState();
        text1 = new Text("test1");
        text2 = new Text("test2");
        reply = new Reply("character", "line");
        menu = new Menu();
        option1 = new Option("option1");
        option2 = new ConditionalOption("option2", "condition");
        aSwitch = new Switch("test");
        endPoint1 = new EndPoint();
        endPoint2 = new EndPoint();

        entryState.setNext(text1);
        text1.setNext(reply);
        reply.setNext(menu);
        menu.addBranch(option1);
        menu.addBranch(option2);
        option1.setNext(aSwitch);
        option2.setNext(aSwitch);
        aSwitch.setTrueBranch(text2);
        aSwitch.setFalseBranch(endPoint1);
        text2.setNext(endPoint2);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(entryState, text1, reply, menu, option1, aSwitch, text2,
                endPoint2, endPoint1, option2);
        testAccept(expected, new ParentFirstFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(endPoint2, text2, endPoint1, aSwitch, option1, option2, menu,
                reply, text1, entryState);
        testAccept(expected, new ChildFirstFlowchartMouseStrategy());
    }

    private void testAccept(List<State> expected, FlowchartMouseStrategy strategy) {
        List<State> states = new ArrayList<>();

        FlowchartContract contract = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        FlowchartMouse mouse = new FlowchartMouse(contract, strategy);
        entryState.accept(mouse);

        Assertions.assertEquals(expected, states);
    }
}
