package com.dododo.ariadne.core.statement.operand;

import java.util.Objects;

public abstract class CompareOperand extends Operand {

    public static final Operand LESS_THAN = new CompareOperand("<") {
        @Override
        public boolean applyAsBoolean(double v1, double v2) {
            return v1 < v2;
        }
    };

    public static final Operand LESS_OR_EQUAL_THAN = new CompareOperand("<=") {
        @Override
        public boolean applyAsBoolean(double v1, double v2) {
            return v1 <= v2;
        }
    };

    public static final Operand GREATER_THAN = new CompareOperand(">") {
        @Override
        public boolean applyAsBoolean(double v1, double v2) {
            return v1 > v2;
        }
    };

    public static final Operand GREATER_OR_EQUAL_THAN = new CompareOperand(">=") {
        @Override
        public boolean applyAsBoolean(double v1, double v2) {
            return v1 >= v2;
        }
    };

    public static final Operand EQUAL = new Operand(1) {

        @Override
        public Object apply(Object v1, Object v2) {
            return Objects.equals(v1, v2);
        }

        @Override
        public String toString() {
            return String.format("%s(==)", getClass().getSimpleName());
        }
    };

    public static final Operand NOT_EQUAL = new Operand(1) {

        @Override
        public Object apply(Object v1, Object v2) {
            return !Objects.equals(v1, v2);
        }

        @Override
        public String toString() {
            return String.format("%s(!=)", getClass().getSimpleName());
        }
    };

    private final String value;

    private CompareOperand(String value) {
        super(1);
        this.value = value;
    }

    @Override
    public final Object apply(Object o1, Object o2) {
        double v1 = prepareValue(o1);
        double v2 = prepareValue(o2);

        return applyAsBoolean(v1, v2);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), value);
    }

    public abstract boolean applyAsBoolean(double v1, double v2);

    private static double prepareValue(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }

        if (o instanceof String) {
            return Double.parseDouble((String) o);
        }

        throw new IllegalArgumentException();
    }
}
