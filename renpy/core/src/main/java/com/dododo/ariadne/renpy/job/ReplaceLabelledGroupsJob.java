package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class ReplaceLabelledGroupsJob extends RemoveComplexStatesJob {

    private final StateCollector<LabelledGroupComplexState> labelledGroupStateCollector;

    public ReplaceLabelledGroupsJob() {
        super();

        this.labelledGroupStateCollector =
                new GenericStateCollector<>(new RenPyFlowchartMouse(), LabelledGroupComplexState.class);
    }

    @Override
    public void run() {
        labelledGroupStateCollector.collect(getFlowchart()).forEach(group -> {
            Marker marker = new Marker(group.getValue());

            joinChildren(group);

            marker.setNext(group.childAt(0));
            group.childAt(0)
                    .removeRoot(group);

            RenPyFlowchartManipulatorUtil.replace(group, marker);
        });
    }
}
