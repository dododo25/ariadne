package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class AddMissingSwitchFalseBranchComplexStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("AddMissingSwitchFalseBranchComplexStatesJob.expected.xml") State expected,
                               @InputParam("AddMissingSwitchFalseBranchComplexStatesJob.setup.xml") State setup) {
        AddMissingSwitchFalseBranchComplexStateJob job = new AddMissingSwitchFalseBranchComplexStateJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new ExtendedFlowchartMouse());
    }
}
