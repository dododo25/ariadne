package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveSkipComplexStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveSkipComplexStatesJob.expected.xml") JaxbState expected,
                               @InputParam("RemoveSkipComplexStatesJob.setup.xml") JaxbState setup) {
        RemoveSkipComplexStatesJob job = new RemoveSkipComplexStatesJob(setup);
        job.run();
        JaxbAssertions.assertEquals(expected, setup);
    }
}
