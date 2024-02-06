package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.composer.ParentFirstLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

public final class RemoveEndPointDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContractComposer composer = selectComposerBasedOnFlowchartTreeSize(
                new ParentFirstLargeTreeFlowchartContractComposer(),
                new FlowchartContractComposer());

        EndPoint commonEndPoint = new EndPoint();
        StateCollector<EndPoint> endPointStateCollector = new GenericStateCollector<>(composer, EndPoint.class);

        endPointStateCollector.collect(getFlowchart())
                .forEach(point -> StateManipulatorUtil.replace(point, commonEndPoint));
    }
}
