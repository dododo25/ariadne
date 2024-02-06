package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.xml.common.composer.XmlFlowchartContractComposer;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JoinGoToPointsJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<Marker> markerCollector =
                new GenericStateCollector<>(new XmlFlowchartContractComposer(), Marker.class);
        StateCollector<GoToPoint> goToPointCollector =
                new GenericStateCollector<>(new XmlFlowchartContractComposer(), GoToPoint.class);

        Map<String, Marker> markers = markerCollector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(SimpleState::getValue, Function.identity()));

        goToPointCollector.collect(getFlowchart())
                        .forEach(point -> process(point, markers));
    }

    private void process(GoToPoint point, Map<String, Marker> markers) {
        Marker marker = markers.get(point.getValue());

        if (marker != null) {
            XmlStateManipulatorUtil.replace(point, marker);
        }
    }
}
