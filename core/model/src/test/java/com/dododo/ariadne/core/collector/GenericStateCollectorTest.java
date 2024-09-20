package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class GenericStateCollectorTest {

    private static StateCollector<? extends State> stateCollector;

    private static StateCollector<Text> textCollector;

    @BeforeAll
    static void setUp() {
        stateCollector = new GenericStateCollector<>(new FlowchartMouse(), State.class);
        textCollector = new GenericStateCollector<>(new FlowchartMouse(), Text.class);
    }

    @Test
    void testCollectShouldReturnCollection() {
        Text state1 = new Text("test1");
        Text state2 = new Text("test2");
        EndPoint state3 = new EndPoint();

        Set<State> expected1 = new HashSet<>(Arrays.asList(state1, state2, state3));
        Set<State> expected2 = new HashSet<>(Arrays.asList(state1, state2));

        state1.setNext(state2);
        state2.setNext(state3);

        Assertions.assertEquals(expected1, stateCollector.collect(state1));
        Assertions.assertEquals(expected2, textCollector.collect(state1));
    }
}
