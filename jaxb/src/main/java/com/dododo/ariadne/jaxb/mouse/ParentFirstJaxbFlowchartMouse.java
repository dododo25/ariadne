package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.SimpleJaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class ParentFirstJaxbFlowchartMouse extends JaxbFlowchartMouse {

    public ParentFirstJaxbFlowchartMouse() {
        this(new ParentFirstJaxbFlowchartMouseStrategy());
    }

    protected ParentFirstJaxbFlowchartMouse(JaxbFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void accept(JaxbState state, Consumer<JaxbState> consumer) {
        JaxbFlowchartContract callback = new SimpleJaxbFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    public void accept(JaxbState state, JaxbFlowchartContract callback) {
        List<JaxbState> grayStates = new ArrayList<>();
        List<JaxbState> blackStates = new ArrayList<>();

        state.accept(strategy, callback, grayStates, blackStates);

        while (!grayStates.isEmpty()) {
            Collection<JaxbState> newGrayStates = new ArrayList<>();

            grayStates.stream().findFirst().ifPresent(nextState -> {
                grayStates.remove(nextState);
                nextState.accept(strategy, callback, newGrayStates, blackStates);
            });

            grayStates.addAll(0, newGrayStates);
        }
    }
}
