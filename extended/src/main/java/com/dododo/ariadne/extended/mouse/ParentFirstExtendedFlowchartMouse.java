package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.mouse.strategy.ParentFirstExtendedFlowchartMouseStrategy;

import java.util.function.Consumer;

public class ParentFirstExtendedFlowchartMouse extends ParentFirstFlowchartMouse {

    public ParentFirstExtendedFlowchartMouse() {
        super(new ParentFirstExtendedFlowchartMouseStrategy());
    }

    @Override
    public void accept(State state, Consumer<State> consumer) {
        ExtendedFlowchartContract callback = new ExtendedSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }
}
