package com.dododo.ariadne.core.statement.operand;

public abstract class Operand {

    protected final int level;

    protected Operand(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public abstract Object apply(Object o1, Object o2);
}
