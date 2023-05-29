package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
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
    private static Statement statement1;
    private static Statement statement2;
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
        statement1 = new Statement("test1");
        statement2 = new Statement("test2");
        reply = new Reply("character", "line");
        menu = new Menu();
        option1 = new Option("option1");
        option2 = new ConditionalOption("option2", "condition");
        aSwitch = new Switch("test");
        endPoint1 = new EndPoint();
        endPoint2 = new EndPoint();

        entryState.setNext(statement1);
        statement1.setNext(reply);
        reply.setNext(menu);
        menu.addBranch(option1);
        menu.addBranch(option2);
        option1.setNext(aSwitch);
        option2.setNext(aSwitch);
        aSwitch.setTrueBranch(statement2);
        aSwitch.setFalseBranch(endPoint1);
        statement2.setNext(endPoint2);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(entryState, statement1, reply, menu, option1, aSwitch, statement2,
                endPoint2, endPoint1, option2);
        testAccept(expected, new ParentFirstFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<State> expected = Arrays.asList(endPoint2, statement2, endPoint1, aSwitch, option1, option2, menu,
                reply, statement1, entryState);
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
