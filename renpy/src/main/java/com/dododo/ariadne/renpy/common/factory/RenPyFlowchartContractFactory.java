package com.dododo.ariadne.renpy.common.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.RenPyFlowchartMouseStrategy;

import java.util.function.Consumer;

public class RenPyFlowchartContractFactory extends FlowchartContractFactory {

    public RenPyFlowchartContractFactory() {
        this(new ParentFirstRenPyFlowchartMouseStrategy());
    }

    public RenPyFlowchartContractFactory(RenPyFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public RenPyFlowchartMouse createFor(Consumer<State> consumer) {
        RenPyFlowchartContract contract = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return createFor(contract);
    }

    @Override
    public RenPyFlowchartMouse createFor(FlowchartContract callback) {
        return new RenPyFlowchartMouse((RenPyFlowchartContract) callback, (RenPyFlowchartMouseStrategy) strategy);
    }
}
