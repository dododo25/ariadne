package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveComplexStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveComplexStatesJob.expected.xml") State expected,
                               @InputParam("RemoveComplexStatesJob.setup.xml") State setup) {
        RemoveComplexStatesJob job = new RemoveComplexStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}
