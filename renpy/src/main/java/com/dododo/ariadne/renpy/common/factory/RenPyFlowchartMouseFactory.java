package com.dododo.ariadne.renpy.common.factory;

import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;

import java.util.function.Consumer;

public class RenPyFlowchartMouseFactory extends FlowchartMouseFactory {

    @Override
    public FlowchartMouse createFor(Consumer<State> consumer) {
        RenPyFlowchartContract contract = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return new RenPyFlowchartMouse(contract, new ParentFirstRenPyFlowchartMouseStrategy());
    }
}
