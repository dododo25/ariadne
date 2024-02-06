package com.dododo.ariadne.xml.common.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class XmlFlowchartContractFactoryTest {

    private static FlowchartContractFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new XmlFlowchartContractFactory();
    }

    @Test
    void testCreateForShouldDoneWell() {
        testCreateFor(new EntryState());
        testCreateFor(new Text("test"));
        testCreateFor(new Switch("test"));
        testCreateFor(new EndPoint());

        testCreateFor(new ComplexState());
        testCreateFor(new ComplexSwitch());
        testCreateFor(new SwitchBranch("test"));
        testCreateFor(new Marker("test"));
        testCreateFor(new GoToPoint("test"));
        testCreateFor(new PassState());
    }

    private void testCreateFor(State state) {
        FlowchartContract mouse = factory.createFor(s -> Assertions.assertSame(s, state));
        state.accept(mouse);
    }
}
