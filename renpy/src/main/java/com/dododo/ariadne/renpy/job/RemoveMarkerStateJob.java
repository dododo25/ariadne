package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.contract.RenPyGenericFlowchartContract;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

import java.util.stream.Stream;

public final class RemoveMarkerStateJob extends AbstractJob {

    private final StateCollector<Marker> collector;

    public RemoveMarkerStateJob() {
        this.collector = new GenericStateCollector<>(new RenPyFlowchartMouse(), Marker.class);
    }

    @Override
    public void run() {
        ((RootComplexState) getFlowchart()).childrenStream()
                .forEach(this::forEachChild);
    }

    private void forEachChild(State state) {
        FlowchartContract firstCallback = new RenPyGenericFlowchartContract() {
            @Override
            public void acceptComplexState(ComplexState complexState) {
                complexState.childrenStream().forEach(RemoveMarkerStateJob.this::process);
            }

            @Override
            public void acceptChainState(ChainState chainState) {
                if (chainState.getNext() != null) {
                    process(chainState.getNext());
                }
            }

            @Override
            public void accept(Switch aSwitch) {
                if (aSwitch.getTrueBranch() != null) {
                    process(aSwitch.getTrueBranch());
                }

                if (aSwitch.getFalseBranch() != null) {
                    process(aSwitch.getFalseBranch());
                }
            }
        };

        FlowchartContract secondCallback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(Marker marker) {
                RenPyFlowchartManipulatorUtil.replace(marker, marker.getNext());
            }
        };

        state.accept(firstCallback);
        state.accept(secondCallback);
    }

    private void process(State state) {
        collector.collect(state).forEach(marker -> {
            Stream.of(marker.getRoots())
                    .filter(RootComplexState.class::isInstance)
                    .map(RootComplexState.class::cast)
                    .forEach(rootComplexState -> rootComplexState.removeChild(marker));

            RenPyFlowchartManipulatorUtil.replace(marker, marker.getNext());
        });
    }
}