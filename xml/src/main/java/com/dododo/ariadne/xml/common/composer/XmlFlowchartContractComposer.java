package com.dododo.ariadne.xml.common.composer;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.mouse.strategy.XmlFlowchartMouseStrategy;

import java.util.function.Consumer;

public class XmlFlowchartContractComposer extends FlowchartContractComposer {

    public XmlFlowchartContractComposer() {
        this(new ParentFirstXmlFlowchartMouseStrategy());
    }

    public XmlFlowchartContractComposer(XmlFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void process(State state, Consumer<State> consumer) {
        XmlFlowchartContract contract = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        process(state, contract);
    }

    @Override
    public void process(State state, FlowchartContract callback) {
        state.accept(new XmlFlowchartMouse((XmlFlowchartContract) callback, (XmlFlowchartMouseStrategy) strategy));
    }
}
