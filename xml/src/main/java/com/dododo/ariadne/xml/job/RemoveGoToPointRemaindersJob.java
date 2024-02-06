package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartContractFactory;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveGoToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<GoToPoint> linkJumpPointStateCollector
                = new GenericStateCollector<>(new XmlFlowchartContractFactory(), GoToPoint.class);
        linkJumpPointStateCollector.collect(getFlowchart())
                .forEach(this::process);
    }

    private void process(GoToPoint point) {
        XmlStateManipulatorUtil.replace(point, new EndPoint());
    }
}
