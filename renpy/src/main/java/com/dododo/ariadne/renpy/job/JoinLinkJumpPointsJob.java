package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JoinLinkJumpPointsJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<LabelledGroup> collector = new GenericStateCollector<>(new RenPyFlowchartMouseFactory(), LabelledGroup.class);

        Map<String, LabelledGroup> links = collector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(SimpleState::getValue, Function.identity()));

        links.values().forEach(link -> process(link, links));
    }

    private void process(LabelledGroup group, Map<String, LabelledGroup> links) {
        RenPyFlowchartContract contract = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(JumpToPoint point) {
                LabelledGroup labelledGroup = links.get(point.getValue());

                if (labelledGroup != null) {
                    RenPyStateManipulatorUtil.replace(point, labelledGroup);
                }
            }
        };

        FlowchartMouse mouse = new RenPyFlowchartMouse(contract, new ParentFirstRenPyFlowchartMouseStrategy());
        group.accept(mouse);
    }
}
