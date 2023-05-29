package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinStatesJob.expected.xml") State expected,
                               @InputParam("JoinStatesJob.setup.xml") JaxbState setup) {
        JoinStatesJob job = new JoinStatesJob(setup);
        job.run();
        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouseFactory());
    }
}
