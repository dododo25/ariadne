package com.dododo.ariadne.xml.common.mouse;

import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;

import java.util.function.Consumer;

public class ParentFirstXmlFlowchartMouse extends ParentFirstFlowchartMouse {

    public ParentFirstXmlFlowchartMouse() {
        super(new ParentFirstXmlFlowchartMouseStrategy());
    }

    @Override
    public void accept(State state, Consumer<State> consumer) {
        XmlFlowchartContract contract = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, contract);
    }
}
