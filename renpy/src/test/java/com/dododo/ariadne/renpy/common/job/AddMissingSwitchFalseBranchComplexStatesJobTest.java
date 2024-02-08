package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class AddMissingSwitchFalseBranchComplexStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("AddMissingSwitchFalseBranchComplexStatesJob.expected.xml") JaxbState expected,
                               @InputParam("AddMissingSwitchFalseBranchComplexStatesJob.setup.xml") JaxbState setup) {
        AddMissingSwitchFalseBranchComplexStateJob job = new AddMissingSwitchFalseBranchComplexStateJob(setup);
        job.run();
        JaxbAssertions.assertEquals(expected, setup);
    }
}
