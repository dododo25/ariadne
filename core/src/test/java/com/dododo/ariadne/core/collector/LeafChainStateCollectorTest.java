package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class LeafChainStateCollectorTest {

    private static StateCollector<ChainState> collector;

    @BeforeAll
    static void setUp() {
        collector = new LeafChainStateCollector(new FlowchartMouseFactory());
    }

    @Test
    void testCollectShouldReturnCollection() {
        Statement statement1 = new Statement("statement1");
        Statement statement2 = new Statement("statement2");

        Switch aSwitch1 = new Switch("switch1");
        Switch aSwitch2 = new Switch("switch2");

        EndPoint endPoint = new EndPoint();

        aSwitch1.setTrueBranch(statement1);
        aSwitch1.setFalseBranch(aSwitch2);
        aSwitch2.setTrueBranch(statement2);
        statement2.setNext(endPoint);

        Assertions.assertEquals(Collections.singleton(statement1), collector.collect(aSwitch1));
    }
}
