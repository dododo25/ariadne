package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class GenericStateCollectorTest {

    private static StateCollector<? extends State> stateCollector;

    private static StateCollector<Statement> statementCollector;

    @BeforeAll
    static void setUp() {
        stateCollector = new GenericStateCollector<>(new FlowchartMouseFactory(), State.class);
        statementCollector = new GenericStateCollector<>(new FlowchartMouseFactory(), Statement.class);
    }

    @Test
    void testCollectShouldReturnCollection() {
        Statement state1 = new Statement("test1");
        Statement state2 = new Statement("test2");
        EndPoint state3 = new EndPoint();

        Set<State> expected1 = new HashSet<>(Arrays.asList(state1, state2, state3));
        Set<State> expected2 = new HashSet<>(Arrays.asList(state1, state2));

        state1.setNext(state2);
        state2.setNext(state3);

        Assertions.assertEquals(expected1, stateCollector.collect(state1));
        Assertions.assertEquals(expected2, statementCollector.collect(state1));
    }
}
