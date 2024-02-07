package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class JoinLinkJumpPointsJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        StateCollector<LabelledGroup> subGroupCollector = new GenericStateCollector<>(mouse, LabelledGroup.class);
        StateCollector<Menu> menuCollector = new GenericStateCollector<>(mouse, Menu.class);
        StateCollector<JumpToPoint> jumpToPointCollector = new GenericStateCollector<>(mouse, JumpToPoint.class);

        Map<String, State> links = new HashMap<>();

        subGroupCollector.collect(getFlowchart())
                .forEach(state -> links.put(state.getValue(), state));

        menuCollector.collect(getFlowchart())
                .stream()
                .filter(menu -> menu.getValue() != null)
                .forEach(menu -> links.put(menu.getValue(), menu));

        jumpToPointCollector.collect(getFlowchart()).forEach(point -> {
            State link = links.get(point.getValue());

            if (link != null) {
                RenPyStateManipulatorUtil.replace(point, link);
            }
        });
    }
}
