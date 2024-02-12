package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class ParentFirstFlowchartMouse extends FlowchartMouse {

    public ParentFirstFlowchartMouse() {
        super(new ParentFirstFlowchartMouseStrategy());
    }

    protected ParentFirstFlowchartMouse(ParentFirstFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void accept(State state, Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    public void accept(State state, FlowchartContract callback) {
        List<State> grayStates = new ArrayList<>();
        List<State> blackStates = new ArrayList<>();

        state.accept(strategy, callback, grayStates, blackStates);

        while (!grayStates.isEmpty()) {
            Collection<State> newGrayStates = new ArrayList<>();

            grayStates.stream().findFirst().ifPresent(nextState -> {
                grayStates.remove(nextState);
                nextState.accept(strategy, callback, newGrayStates, blackStates);
            });

            grayStates.addAll(0, newGrayStates);
        }
    }
}
