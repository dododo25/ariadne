package com.dododo.ariadne.core.statement.operand;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.dododo.ariadne.core.statement.operand.CompareOperand.EQUAL;
import static com.dododo.ariadne.core.statement.operand.CompareOperand.GREATER_OR_EQUAL_THAN;
import static com.dododo.ariadne.core.statement.operand.CompareOperand.GREATER_THAN;
import static com.dododo.ariadne.core.statement.operand.CompareOperand.LESS_OR_EQUAL_THAN;
import static com.dododo.ariadne.core.statement.operand.CompareOperand.LESS_THAN;
import static com.dododo.ariadne.core.statement.operand.CompareOperand.NOT_EQUAL;

class CompareOperandTest {

    @Test
    void testApplyWhenArgumentsStackedCorrectlyShouldReturnObject() {
        Assertions.assertEquals(true, LESS_THAN.apply(3, 4));
        Assertions.assertEquals(true, LESS_THAN.apply(3.5, 4));
        Assertions.assertEquals(true, LESS_THAN.apply(3, 4.5));

        Assertions.assertEquals(true, LESS_OR_EQUAL_THAN.apply(3, 3));
        Assertions.assertEquals(true, LESS_OR_EQUAL_THAN.apply(3, 4));
        Assertions.assertEquals(true, LESS_OR_EQUAL_THAN.apply(3.5, 4));
        Assertions.assertEquals(true, LESS_OR_EQUAL_THAN.apply(3, 4.5));

        Assertions.assertEquals(true, GREATER_THAN.apply(4, 3));
        Assertions.assertEquals(true, GREATER_THAN.apply(4, 3.5));
        Assertions.assertEquals(true, GREATER_THAN.apply(4.5, 3));

        Assertions.assertEquals(true, GREATER_OR_EQUAL_THAN.apply(3, 3));
        Assertions.assertEquals(true, GREATER_OR_EQUAL_THAN.apply(4, 3));
        Assertions.assertEquals(true, GREATER_OR_EQUAL_THAN.apply(4, 3.5));
        Assertions.assertEquals(true, GREATER_OR_EQUAL_THAN.apply(4.5, 3));

        Assertions.assertEquals(true, EQUAL.apply(3, 3));
        Assertions.assertEquals(true, EQUAL.apply(3.5, 3.5));
        Assertions.assertEquals(true, EQUAL.apply("3.5", "3.5"));

        Assertions.assertEquals(true, NOT_EQUAL.apply(3.5, 3));
        Assertions.assertEquals(true, NOT_EQUAL.apply(3, 3.5));
        Assertions.assertEquals(true, NOT_EQUAL.apply(3.5, "3.5"));
        Assertions.assertEquals(true, NOT_EQUAL.apply("3.5", 3.5));
    }

    @Test
    void testApplyWhenArgumentsStackedIncorrectlyShouldReturnObject() {
        Assertions.assertEquals(false, LESS_THAN.apply(3, 3));
        Assertions.assertEquals(false, LESS_THAN.apply(4, 3));
        Assertions.assertEquals(false, LESS_THAN.apply(4, 3.5));
        Assertions.assertEquals(false, LESS_THAN.apply(4.5, 3));

        Assertions.assertEquals(false, LESS_OR_EQUAL_THAN.apply(4, 3));
        Assertions.assertEquals(false, LESS_OR_EQUAL_THAN.apply(4, 3.5));
        Assertions.assertEquals(false, LESS_OR_EQUAL_THAN.apply(4.5, 3));

        Assertions.assertEquals(false, GREATER_THAN.apply(3, 3));
        Assertions.assertEquals(false, GREATER_THAN.apply(3, 4));
        Assertions.assertEquals(false, GREATER_THAN.apply(3.5, 4));
        Assertions.assertEquals(false, GREATER_THAN.apply(3, 4.5));

        Assertions.assertEquals(false, GREATER_OR_EQUAL_THAN.apply(3, 4));
        Assertions.assertEquals(false, GREATER_OR_EQUAL_THAN.apply(3.5, 4));
        Assertions.assertEquals(false, GREATER_OR_EQUAL_THAN.apply(3, 4.5));

        Assertions.assertEquals(false, EQUAL.apply(3, 4));
        Assertions.assertEquals(false, EQUAL.apply("3", 3));
        Assertions.assertEquals(false, EQUAL.apply(3, "3"));

        Assertions.assertEquals(false, NOT_EQUAL.apply(3, 3));
        Assertions.assertEquals(false, NOT_EQUAL.apply("3", "3"));
    }
}
