package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.ParentFirstJaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.contract.SimpleRenPyJaxbFlowchartContract;

import java.util.function.Consumer;

public class ParentFirstRenPyJaxbFlowchartMouse extends ParentFirstJaxbFlowchartMouse {

    public ParentFirstRenPyJaxbFlowchartMouse() {
        super(new ParentFirstJaxbFlowchartMouseStrategy());
    }

    @Override
    public void accept(JaxbState state, Consumer<JaxbState> consumer) {
        JaxbFlowchartContract callback = new SimpleRenPyJaxbFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }
}
