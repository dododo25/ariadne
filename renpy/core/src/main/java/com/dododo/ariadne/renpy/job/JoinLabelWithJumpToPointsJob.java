package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class JoinLabelWithJumpToPointsJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        StateCollector<Marker> labelCollector = new GenericStateCollector<>(mouse, Marker.class);
        StateCollector<GoToPoint> goToPointCollector = new GenericStateCollector<>(mouse, GoToPoint.class);

        Map<String, Marker> markers = new HashMap<>();

        labelCollector.collect(getFlowchart())
                .forEach(state -> markers.put(state.getValue(), state));

        goToPointCollector.collect(getFlowchart()).forEach(point -> {
            Marker marker = markers.get(point.getValue());

            if (marker != null) {
                RenPyFlowchartManipulatorUtil.replace(point, marker);
            }
        });
    }
}
