package com.dododo.ariadne.renpy.common.composer;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.RenPyFlowchartMouseStrategy;

import java.util.function.Consumer;

public class RenPyFlowchartContractComposer extends FlowchartContractComposer {

    public RenPyFlowchartContractComposer() {
        this(new ParentFirstRenPyFlowchartMouseStrategy());
    }

    public RenPyFlowchartContractComposer(RenPyFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void process(State state, Consumer<State> consumer) {
        RenPyFlowchartContract callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        process(state, callback);
    }

    @Override
    public void process(State state, FlowchartContract callback) {
        state.accept(new RenPyFlowchartMouse(
                (RenPyFlowchartContract) callback,
                (RenPyFlowchartMouseStrategy) strategy));
    }
}
