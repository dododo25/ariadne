package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.mouse.ParentFirstExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedStateManipulatorUtil;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JoinLabelWithGoToPointsJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<Label> labelCollector =
                new GenericStateCollector<>(new ParentFirstExtendedFlowchartMouse(), Label.class);
        StateCollector<GoToPoint> goToPointCollector =
                new GenericStateCollector<>(new ParentFirstExtendedFlowchartMouse(), GoToPoint.class);

        Map<String, Label> labels = labelCollector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(SimpleState::getValue, Function.identity()));

        goToPointCollector.collect(getFlowchart())
                        .forEach(point -> process(point, labels));
    }

    private void process(GoToPoint point, Map<String, Label> labels) {
        Label label = labels.get(point.getValue());

        if (label != null) {
            ExtendedStateManipulatorUtil.replace(point, label);
        }
    }
}
