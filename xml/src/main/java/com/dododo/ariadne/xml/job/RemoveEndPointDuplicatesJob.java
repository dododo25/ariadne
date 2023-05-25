package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveEndPointDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        EndPoint commonEndPoint = new EndPoint();
        StateCollector<EndPoint> endPointStateCollector =
                new GenericStateCollector<>(new FlowchartMouseFactory(), EndPoint.class);

        endPointStateCollector.collect(getFlowchart())
                .forEach(point -> XmlStateManipulatorUtil.replace(point, commonEndPoint));
    }
}
