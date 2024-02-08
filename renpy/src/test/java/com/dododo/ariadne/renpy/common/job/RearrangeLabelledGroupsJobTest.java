package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.renpy.jaxb.test.util.FlowchartRootsRestoreUtil;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RearrangeLabelledGroupsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RearrangeLabelledGroupsJob.expected.xml") JaxbState expected,
                               @InputParam("RearrangeLabelledGroupsJob.setup.xml") JaxbState setup) {
        FlowchartRootsRestoreUtil.restore(setup);

        RearrangeLabelledGroupsJob job = new RearrangeLabelledGroupsJob(setup);
        job.run();
        JaxbAssertions.assertEquals(expected, setup);
    }
}
