package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class JoinLinkJumpPointsJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory factory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory());

        StateCollector<LabelledGroup> subGroupCollector = new GenericStateCollector<>(factory, LabelledGroup.class);
        StateCollector<Menu> menuCollector = new GenericStateCollector<>(factory, Menu.class);
        StateCollector<JumpToPoint> jumpToPointCollector = new GenericStateCollector<>(factory, JumpToPoint.class);

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
