package com.dododo.ariadne.core.statement.operand;

public abstract class MathOperand extends Operand {

    public static final Operand PLUS = new MathOperand("+", 2, false) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return v1 + v2;
        }
    };

    public static final Operand MINUS = new MathOperand("-", 2, true) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return v1 - v2;
        }
    };

    public static final Operand MULTIPLY = new MathOperand("*", 3, false) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return v1 * v2;
        }
    };

    public static final Operand DIVIDE = new MathOperand("\\", 3, false) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return v1 / v2;
        }
    };

    public static final Operand FLOOR_DIVIDE = new MathOperand("\\\\", 3, false) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return Math.floor(v1 / v2);
        }
    };

    public static final Operand REMAINDER = new MathOperand("%", 3, false) {
        @Override
        public double applyAsDouble(double v1, double v2) {
            return (v1 % v2 + v2) % v2;
        }
    };

    private final String value;

    private final boolean special;

    private MathOperand(String value, int level, boolean special) {
        super(level);
        this.value = value;
        this.special = special;
    }

    public boolean isSpecial() {
        return special;
    }

    @Override
    public final Object apply(Object o1, Object o2) {
        double v1 = prepareValue(o1);
        double v2 = prepareValue(o2);
        
        return applyAsDouble(v1, v2);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), value);
    }
    
    public abstract double applyAsDouble(double v1, double v2);
    
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
