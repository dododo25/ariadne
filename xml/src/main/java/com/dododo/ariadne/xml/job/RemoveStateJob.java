package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveStateJob<T extends ChainState> extends AbstractJob {

    private final Class<T> type;

    public RemoveStateJob(Class<T> type) {
        this.type = type;
    }

    @Override
    public void run() {
        StateCollector<T> collector = new GenericStateCollector<>(new XmlFlowchartMouseFactory(), type);
        State flowchart = getFlowchart();

        collector.collect(flowchart)
                .forEach(this::process);

        if (flowchart.getClass().equals(type)) {
            State newRoot = ((ChainState) flowchart).getNext();
            newRoot.removeRoot(flowchart);
            setFlowchart(newRoot);
        }
    }

    private void process(T state) {
        XmlStateManipulatorUtil.replace(state, state.getNext());
    }
}
