package com.dododo.ariadne.xml.common.factory;

import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;

import java.util.function.Consumer;

public class XmlFlowchartMouseFactory extends FlowchartMouseFactory {

    @Override
    public FlowchartMouse createFor(Consumer<State> consumer) {
        XmlFlowchartContract contract = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return new XmlFlowchartMouse(contract, new ParentFirstXmlFlowchartMouseStrategy());
    }
}
