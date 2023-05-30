package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareJaxbComplexSwitchStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareJaxbComplexSwitchStatesJob.expected.xml") JaxbState expected,
                               @InputParam("PrepareJaxbComplexSwitchStatesJob.setup.xml") JaxbState setup) {
        PrepareJaxbComplexSwitchStatesJob job = new PrepareJaxbComplexSwitchStatesJob(setup);
        job.run();
        JaxbAssertions.assertEquals(expected, setup);
    }
}
