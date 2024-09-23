package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

import java.util.stream.Stream;

public final class RearrangeLabelledGroupsJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<LabelledGroupComplexState> collector =
                new GenericStateCollector<>(new RenPyFlowchartMouse(), LabelledGroupComplexState.class);

        collector.collect(getFlowchart())
                .stream()
                .filter(this::checkStateRootsForLabelledGroup)
                .forEach(s -> {
                    Stream.of(s.getRoots()).map(ComplexState.class::cast).forEach(root -> {
                        root.removeChild(s);
                        root.addChild(new GoToPoint(s.getValue()));
                    });

                    ((ComplexState) getFlowchart()).addChild(s);
                });
    }

    private boolean checkStateRootsForLabelledGroup(LabelledGroupComplexState state) {
        return Stream.of(state.getRoots()).noneMatch(root -> getFlowchart() == root);
    }
}
