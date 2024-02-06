package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.factory.ParentFirstLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

public final class RemoveEndPointDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory factory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstLargeTreeFlowchartContractFactory(),
                new FlowchartContractFactory());

        EndPoint commonEndPoint = new EndPoint();
        StateCollector<EndPoint> endPointStateCollector = new GenericStateCollector<>(factory, EndPoint.class);

        endPointStateCollector.collect(getFlowchart())
                .forEach(point -> StateManipulatorUtil.replace(point, commonEndPoint));
    }
}
