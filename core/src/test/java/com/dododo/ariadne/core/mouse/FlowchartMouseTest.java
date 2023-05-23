package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;
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
    private static Switch aSwitch;
    private static EndPoint endPoint1;
    private static EndPoint endPoint2;

    @BeforeAll
    static void setUp() {
        entryState = new EntryState();
        statement1 = new Statement("statement1");
        statement2 = new Statement("statement2");
        aSwitch = new Switch("switch");
        endPoint1 = new EndPoint();
        endPoint2 = new EndPoint();

        entryState.setNext(statement1);
        statement1.setNext(aSwitch);
        aSwitch.setTrueBranch(statement2);
        aSwitch.setFalseBranch(endPoint1);
        statement2.setNext(endPoint2);
    }

    @Test
    void testAcceptShouldNotThrowException() {
        List<State> expected = Arrays.asList(entryState, statement1, aSwitch,
                statement2, endPoint2, endPoint1);

        List<State> states = new ArrayList<>();

        FlowchartContract contract = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                states.add(state);
            }
        };

        FlowchartMouse mouse = new FlowchartMouse(contract);
        entryState.accept(mouse);

        Assertions.assertEquals(expected, states);
    }
}
