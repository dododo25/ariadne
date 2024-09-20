package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class LeafChainStateCollectorTest {

    private static StateCollector<ChainState> collector;

    @BeforeAll
    static void setUp() {
        collector = new LeafChainStateCollector(new FlowchartMouse());
    }

    @Test
    void testCollectShouldReturnCollection() {
        Text text1 = new Text("text1");
        Text text2 = new Text("text2");

        Switch aSwitch1 = new Switch("switch1");
        Switch aSwitch2 = new Switch("switch2");

        EndPoint endPoint = new EndPoint();

        aSwitch1.setTrueBranch(text1);
        aSwitch1.setFalseBranch(aSwitch2);
        aSwitch2.setTrueBranch(text2);
        text2.setNext(endPoint);

        Assertions.assertEquals(Collections.singleton(text1), collector.collect(aSwitch1));
    }
}
