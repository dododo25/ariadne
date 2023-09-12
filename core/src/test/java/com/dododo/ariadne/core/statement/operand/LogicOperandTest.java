package com.dododo.ariadne.core.statement.operand;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.dododo.ariadne.core.statement.operand.LogicOperand.AND;
import static com.dododo.ariadne.core.statement.operand.LogicOperand.NOT;
import static com.dododo.ariadne.core.statement.operand.LogicOperand.OR;
import static com.dododo.ariadne.core.statement.operand.LogicOperand.XOR;

class LogicOperandTest {

    @Test
    void testApplyWhenArgumentsStackedCorrectlyShouldReturnObject() {
        Assertions.assertEquals(true, AND.apply(true, true));

        Assertions.assertEquals(true, OR.apply(false, true));
        Assertions.assertEquals(true, OR.apply(true, false));
        Assertions.assertEquals(true, OR.apply(true, true));

        Assertions.assertEquals(true, XOR.apply(false, true));
        Assertions.assertEquals(true, XOR.apply(true, false));
    }

    @Test
    void testApplyWhenArgumentsStackedIncorrectlyShouldReturnObject() {
        Assertions.assertEquals(false, AND.apply(false, false));
        Assertions.assertEquals(false, AND.apply(false, true));
        Assertions.assertEquals(false, AND.apply(true, false));

        Assertions.assertEquals(false, OR.apply(false, false));

        Assertions.assertEquals(false, XOR.apply(false, false));
        Assertions.assertEquals(false, XOR.apply(true, true));

        Assertions.assertEquals(false, NOT.apply(false, false));
        Assertions.assertEquals(false, NOT.apply(false, true));
        Assertions.assertEquals(false, NOT.apply(true, false));
        Assertions.assertEquals(false, NOT.apply(true, true));
    }
}
