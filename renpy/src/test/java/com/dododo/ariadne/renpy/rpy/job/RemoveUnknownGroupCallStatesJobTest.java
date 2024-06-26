package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveUnknownGroupCallStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveUnknownGroupCallStatesJob.expected.xml") State expected,
                               @InputParam("RemoveUnknownGroupCallStatesJob.setup.xml") State setup) {
        RemoveUnknownGroupCallStatesJob job = new RemoveUnknownGroupCallStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ParentFirstRenPyFlowchartMouse());
    }
}
