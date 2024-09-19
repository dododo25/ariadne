package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.util.XmlFlowchartManipulatorUtil;

public final class RemoveStateJob<T extends ChainState> extends AbstractJob {

    private final Class<T> type;

    public RemoveStateJob(Class<T> type) {
        this.type = type;
    }

    @Override
    public void run() {
        StateCollector<T> collector = new GenericStateCollector<>(new XmlFlowchartMouse(), type);

        State flowchart = getFlowchart();

        collector.collect(flowchart)
                .forEach(s -> XmlFlowchartManipulatorUtil.replace(s, s.getNext()));

        if (flowchart.getClass().equals(type)) {
            State newRoot = ((ChainState) flowchart).getNext();
            newRoot.removeRoot(flowchart);
            setFlowchart(newRoot);
        }
    }
}
