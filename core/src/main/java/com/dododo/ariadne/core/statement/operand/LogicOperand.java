package com.dododo.ariadne.core.statement.operand;

public abstract class LogicOperand extends Operand {

    public static final Operand AND = new LogicOperand("&&", false) {
        @Override
        public boolean applyAsBoolean(boolean v1, boolean v2) {
            return v1 && v2;
        }
    };

    public static final Operand OR = new LogicOperand("||", false) {
        @Override
        public boolean applyAsBoolean(boolean v1, boolean v2) {
            return v1 || v2;
        }
    };

    public static final Operand XOR = new LogicOperand("^", false) {
        @Override
        public boolean applyAsBoolean(boolean v1, boolean v2) {
            return v1 ^ v2;
        }
    };

    public static final Operand NOT = new LogicOperand("!",true) {
        @Override
        public boolean applyAsBoolean(boolean v1, boolean v2) {
            return false;
        }
    };

    private final String value;

    private final boolean special;

    private LogicOperand(String value, boolean special) {
        super(0);
        this.value = value;
        this.special = special;
    }

    public boolean isSpecial() {
        return special;
    }

    @Override
    public final Object apply(Object o1, Object o2) {
        boolean v1 = prepareValue(o1);
        boolean v2 = prepareValue(o2);
        
        return applyAsBoolean(v1, v2);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), value);
    }
    
    public abstract boolean applyAsBoolean(boolean v1, boolean v2);
    
    private static boolean prepareValue(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }

        if (o instanceof String) {
            return Boolean.parseBoolean((String) o);
        }

        throw new IllegalArgumentException();
    }
}
