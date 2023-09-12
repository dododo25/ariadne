package com.dododo.ariadne.core.statement.operand;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.dododo.ariadne.core.statement.operand.MathOperand.DIVIDE;
import static com.dododo.ariadne.core.statement.operand.MathOperand.FLOOR_DIVIDE;
import static com.dododo.ariadne.core.statement.operand.MathOperand.MINUS;
import static com.dododo.ariadne.core.statement.operand.MathOperand.MULTIPLY;
import static com.dododo.ariadne.core.statement.operand.MathOperand.PLUS;
import static com.dododo.ariadne.core.statement.operand.MathOperand.REMAINDER;

class MathOperandTest {

    @Test
    void testApplyWhenArgumentsStackedCorrectlyShouldReturnObject() {
        Assertions.assertEquals(7.0, PLUS.apply(3, 4));
        Assertions.assertEquals(7.5, PLUS.apply(3.5, 4));
        Assertions.assertEquals(7.5, PLUS.apply(3, 4.5));

        Assertions.assertEquals(1.0, MINUS.apply(4, 3));
        Assertions.assertEquals(1.5, MINUS.apply(4.5, 3));
        Assertions.assertEquals(0.5, MINUS.apply(4, 3.5));

        Assertions.assertEquals(12.0, MULTIPLY.apply(3, 4));
        Assertions.assertEquals(14.0, MULTIPLY.apply(3.5, 4));
        Assertions.assertEquals(13.5, MULTIPLY.apply(3, 4.5));

        Assertions.assertEquals(0.75, DIVIDE.apply(3, 4));
        Assertions.assertEquals(2.125, DIVIDE.apply(8.5, 4));
        Assertions.assertEquals(2.0, DIVIDE.apply(9, 4.5));

        Assertions.assertEquals(0.0, FLOOR_DIVIDE.apply(3, 4));
        Assertions.assertEquals(2.0, FLOOR_DIVIDE.apply(8.5, 4));
        Assertions.assertEquals(2.0, FLOOR_DIVIDE.apply(9, 4.5));

        Assertions.assertEquals(3.0, REMAINDER.apply(3, 4));
        Assertions.assertEquals(0.5, REMAINDER.apply(8.5, 4));
        Assertions.assertEquals(0.0, REMAINDER.apply(9, 4.5));
        Assertions.assertEquals(3.0, REMAINDER.apply(-1, 4));
    }
}
