package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveSkipComplexStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveSkipComplexStatesJob.expected.xml") State expected,
                               @InputParam("RemoveSkipComplexStatesJob.setup.xml") State setup) {
        RemoveSkipComplexStatesJob job = new RemoveSkipComplexStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new RenPyFlowchartMouse());
    }
}