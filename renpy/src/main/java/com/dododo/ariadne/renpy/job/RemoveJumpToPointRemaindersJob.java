package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveJumpToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<JumpToPoint> linkJumpPointStateCollector
                = new GenericStateCollector<>(new RenPyFlowchartMouseFactory(), JumpToPoint.class);
        linkJumpPointStateCollector.collect(getFlowchart())
                .forEach(this::process);
    }

    private void process(JumpToPoint point) {
        RenPyStateManipulatorUtil.replace(point, new EndPoint());
    }
}
