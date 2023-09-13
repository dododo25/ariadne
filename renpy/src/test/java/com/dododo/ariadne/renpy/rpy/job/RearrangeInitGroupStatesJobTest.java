package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RearrangeInitGroupStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RearrangeInitGroupStatesJob.expected.xml") JaxbState expected,
                               @InputParam("RearrangeInitGroupStatesJob.setup.xml") JaxbState setup) {
        RearrangeInitGroupStatesJob job = new RearrangeInitGroupStatesJob(setup);
        job.run();
        JaxbAssertions.assertEquals(expected, setup);
    }
}
