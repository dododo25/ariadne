package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public final class ConditionalOption extends Option {

    private final String condition;

    public ConditionalOption(String value, String condition) {
        super(value);
        this.condition = Objects.requireNonNull(condition);
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof ConditionalOption
                && Objects.equals(value, ((ConditionalOption) o).value)
                && Objects.equals(condition, ((ConditionalOption) o).condition) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s', condition='%s')", getClass().getSimpleName(), value, condition);
    }
}
