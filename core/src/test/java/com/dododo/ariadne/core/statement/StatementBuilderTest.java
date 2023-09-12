package com.dododo.ariadne.core.statement;

import com.dododo.ariadne.core.statement.operand.LogicOperand;
import com.dododo.ariadne.core.statement.operand.MathOperand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class StatementBuilderTest {

    @Test
    void testBuildWhenOperandsAreInvalidShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .addParam(Parameter.constant(2))
                .addOperation(MathOperand.PLUS)
                .addOperation(MathOperand.PLUS)
                .addParam(Parameter.constant(2));

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenNoLeftOperandProvidedShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .addOperation(MathOperand.PLUS)
                .addParam(Parameter.constant(2));

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenNoRightOperandProvidedShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .addParam(Parameter.constant(2))
                .addOperation(MathOperand.PLUS);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenNoOperandBetweenProvidedShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .addParam(Parameter.constant(2))
                .addParam(Parameter.constant(2));

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenBracketsWasNotClosedShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .openBracket()
                .addParam(Parameter.constant(2))
                .addOperation(MathOperand.PLUS)
                .addParam(Parameter.constant(2));

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenBracketsWasTooClosedShouldThrowException() {
        Statement.Builder builder = new Statement.Builder()
                .openBracket()
                .addParam(Parameter.constant(2))
                .addOperation(MathOperand.PLUS)
                .addParam(Parameter.constant(2))
                .closeBracket()
                .closeBracket();

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testBuildWhenSpecialLogicOperandExistsShouldReturnObject() throws IllegalArgumentException {
        Statement statement = new Statement.Builder()
                .addOperation(LogicOperand.NOT)
                .addParam(Parameter.constant(true))
                .addOperation(LogicOperand.AND)
                .addParam(Parameter.constant(true))
                .build();

        Assertions.assertEquals(false, statement.process(Collections.emptyMap()));
    }

    @Test
    void testBuildWhenSpecialMathOperandExistsShouldReturnObject() throws IllegalArgumentException {
        Statement statement = new Statement.Builder()
                .addOperation(MathOperand.MINUS)
                .addParam(Parameter.constant(1))
                .addOperation(MathOperand.MULTIPLY)
                .addParam(Parameter.constant(5))
                .build();

        Assertions.assertEquals(-5, statement.process(Collections.emptyMap()));
    }

    @Test
    void testBuildWhenBracketsExistsShouldReturnObject() throws IllegalArgumentException {
        Statement statement = new Statement.Builder()
                .openBracket()
                .addParam(Parameter.constant(2))
                .addOperation(MathOperand.PLUS)
                .addParam(Parameter.constant(2))
                .closeBracket()
                .addOperation(MathOperand.MULTIPLY)
                .addParam(Parameter.constant(2))
                .build();

        Assertions.assertEquals(8, statement.process(Collections.emptyMap()));
    }
}
