package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class JoinLabelWithGoToPointsJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        StateCollector<Label> labelCollector = new GenericStateCollector<>(mouse, Label.class);
        StateCollector<Menu> menuCollector = new GenericStateCollector<>(mouse, Menu.class);
        StateCollector<GoToPoint> goToPointCollector = new GenericStateCollector<>(mouse, GoToPoint.class);

        Map<String, State> links = new HashMap<>();

        labelCollector.collect(getFlowchart())
                .forEach(state -> links.put(state.getValue(), state));

        menuCollector.collect(getFlowchart())
                .stream()
                .filter(menu -> menu.getValue() != null)
                .forEach(menu -> links.put(menu.getValue(), menu));

        goToPointCollector.collect(getFlowchart()).forEach(point -> {
            State link = links.get(point.getValue());

            if (link != null) {
                RenPyStateManipulatorUtil.replace(point, link);
            }
        });
    }
}
