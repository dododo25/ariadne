package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;

public abstract class RemoveStateJob<T extends ChainState> extends AbstractJob {

    private final Class<T> type;

    protected RemoveStateJob(Class<T> type) {
        this.type = type;
    }

    @Override
    public void run() {
        StateCollector<T> collector = new GenericStateCollector<>(prepareFactory(), type);

        State flowchart = getFlowchart();

        collector.collect(flowchart)
                .forEach(this::process);

        if (flowchart.getClass().equals(type)) {
            State newRoot = ((ChainState) flowchart).getNext();
            newRoot.removeRoot(flowchart);
            setFlowchart(newRoot);
        }
    }

    protected abstract FlowchartContractFactory prepareFactory();

    protected abstract void process(T state);
}
