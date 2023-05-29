package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class ValidateRawFlowchartJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("ValidateRawStateJobTest.setup1.xml") JaxbState setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob(setup);
        Assertions.assertDoesNotThrow(job::run);
    }

    @Test
    void testRunWhenInvalidComplexStateExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup2.xml") JaxbState setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }

    @Test
    void testRunWhenInvalidMenuExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup3.xml") JaxbState setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }

    @Test
    void testRunWhenInvalidSwitchExistsShouldThrowException(@InputParam("ValidateRawStateJobTest.setup4.xml") JaxbState setup) {
        ValidateRawFlowchartJob job = new ValidateRawFlowchartJob(setup);
        Assertions.assertThrows(AriadneException.class, job::run);
    }
}
