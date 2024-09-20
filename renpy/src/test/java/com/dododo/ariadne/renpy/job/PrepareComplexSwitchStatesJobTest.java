package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareComplexSwitchStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareComplexSwitchStatesJob.expected.xml") State expected,
                               @InputParam("PrepareComplexSwitchStatesJob.setup.xml") State setup) {
        PrepareComplexSwitchStatesJob job = new PrepareComplexSwitchStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new RenPyFlowchartMouse());
    }
}
