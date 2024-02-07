package com.dododo.ariadne.renpy.common.mouse;

import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;

import java.util.function.Consumer;

public class ParentFirstRenPyFlowchartMouse extends ParentFirstFlowchartMouse {

    public ParentFirstRenPyFlowchartMouse() {
        super(new ParentFirstRenPyFlowchartMouseStrategy());
    }

    @Override
    public void accept(State state, Consumer<State> consumer) {
        RenPyFlowchartContract callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }
}
