package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;

public abstract class RemoveStateJob<T extends ChainState> extends AbstractJob {

    private final Class<T> type;

    private final StateCollector<T> collector;

    protected RemoveStateJob(Class<T> type, FlowchartContractFactory factory) {
        this.type = type;
        this.collector = new GenericStateCollector<>(factory, type);
    }

    @Override
    public void run() {
        State flowchart = getFlowchart();

        collector.collect(flowchart)
                .forEach(this::process);

        if (flowchart.getClass().equals(type)) {
            State newRoot = ((ChainState) flowchart).getNext();
            newRoot.removeRoot(flowchart);
            setFlowchart(newRoot);
        }
    }

    protected abstract void process(T state);
}
