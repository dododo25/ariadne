package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinStatesJob.expected1.xml") State expected,
                               @InputParam("JoinStatesJob.setup1.xml") JaxbState setup) {
        JoinStatesJob job = new JoinStatesJob(setup);
        job.run();
        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartContractComposer());
    }

    @Test
    void testRunWhenComplexStateWithoutChildrenExistsShouldDoneWell(@InputParam("JoinStatesJob.expected2.xml") State expected,
                                                                    @InputParam("JoinStatesJob.setup2.xml") JaxbState setup) {
        JoinStatesJob job = new JoinStatesJob(setup);
        job.run();
        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartContractComposer());
    }
}
