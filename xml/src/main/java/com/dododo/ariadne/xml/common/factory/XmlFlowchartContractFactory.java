package com.dododo.ariadne.xml.common.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.mouse.strategy.XmlFlowchartMouseStrategy;

import java.util.function.Consumer;

public class XmlFlowchartContractFactory extends FlowchartContractFactory {

    public XmlFlowchartContractFactory() {
        this(new ParentFirstXmlFlowchartMouseStrategy());
    }

    public XmlFlowchartContractFactory(XmlFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public XmlFlowchartMouse createFor(Consumer<State> consumer) {
        XmlFlowchartContract contract = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return createFor(contract);
    }

    @Override
    public XmlFlowchartMouse createFor(FlowchartContract callback) {
        return new XmlFlowchartMouse((XmlFlowchartContract) callback, (XmlFlowchartMouseStrategy) strategy);
    }
}
