package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RenPyRemoveStateJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RenPyRemoveStateJob.expected.xml") State expected,
                               @InputParam("RenPyRemoveStateJob.setup.xml") State setup) {
        RenPyRemoveStateJob<Text> job = new RenPyRemoveStateJob<>(Text.class);

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}
