package com.dododo.ariadne.core.statement;

import com.dododo.ariadne.core.statement.operand.Operand;

import java.util.Map;
import java.util.Objects;

public final class Operation implements Statement {

    private final Statement left;

    private final Statement right;

    private final Operand operand;

    public Operation(Statement left, Statement right, Operand operand) {
        this.left = left;
        this.right = right;
        this.operand = operand;
    }

    public Object process(Map<String, ?> params) {
        Object leftResult = left.process(params);
        Object rightResult = right.process(params);

        Object result = operand.apply(leftResult, rightResult);

        if (result instanceof Double) {
            double value = (double) result;

            if (value % 1 == 0) {
                value = Math.floor(value);

                if (((long) Math.abs(value)) >>> 32 == 0) {
                    return (int) value;
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format("%s(left=%s, right=%s, operand=%s)", getClass().getSimpleName(), left, right, operand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, operand);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof Operation && equals((Operation) obj);
    }

    private boolean equals(Operation operation) {
        return Objects.equals(operation.left, this.left)
                && Objects.equals(operation.right, this.right)
                && Objects.equals(operation.operand, this.operand);
    }
}
