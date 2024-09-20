package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
@SuppressWarnings({"java:S4144", "java:S5976"})
class ValidateRawFlowchartJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("ValidateRawStateJobTest.setup1.xml") State setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob();
        job.setFlowchart(setup);
        Assertions.assertDoesNotThrow(job::run);
    }

    @Test
    void testRunWhenInvalidComplexStateExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup2.xml") State setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob();
        job.setFlowchart(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }

    @Test
    void testRunWhenInvalidMenuExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup3.xml") State setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob();
        job.setFlowchart(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }

    @Test
    void testRunWhenInvalidSwitchExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup4.xml") State setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob();
        job.setFlowchart(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }
}
