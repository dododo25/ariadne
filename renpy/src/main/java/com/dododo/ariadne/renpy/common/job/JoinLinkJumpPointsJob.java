package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class JoinLinkJumpPointsJob extends AbstractJob {

    private final StateCollector<LabelledGroup> subGroupCollector;

    private final StateCollector<Menu> menuCollector;

    private final StateCollector<JumpToPoint> jumpToPointCollector;

    public JoinLinkJumpPointsJob() {
        subGroupCollector = new GenericStateCollector<>(new RenPyFlowchartContractFactory(), LabelledGroup.class);
        menuCollector = new GenericStateCollector<>(new RenPyFlowchartContractFactory(), Menu.class);
        jumpToPointCollector = new GenericStateCollector<>(new RenPyFlowchartContractFactory(), JumpToPoint.class);
    }

    @Override
    public void run() {
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
