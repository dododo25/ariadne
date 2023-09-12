package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public final class Switch extends State {

    private final String condition;

    private State trueBranch;

    private State falseBranch;

    public Switch(String condition) {
        this.condition = Objects.requireNonNull(condition);
    }

    public String getCondition() {
        return condition;
    }

    public State getTrueBranch() {
        return trueBranch;
    }

    public void setTrueBranch(State state) {
        if (this.trueBranch != null) {
            this.trueBranch.removeRoot(this);
        }

        this.trueBranch = state;

        if (this.trueBranch != null) {
            this.trueBranch.addRoot(this);
        }
    }

    public State getFalseBranch() {
        return falseBranch;
    }

    public void setFalseBranch(State state) {
        if (this.falseBranch != null) {
            this.falseBranch.removeRoot(this);
        }

        this.falseBranch = state;

        if (this.falseBranch != null) {
            this.falseBranch.addRoot(this);
        }
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareBySingleValue(o, state -> ((Switch) state).getCondition());
    }

    @Override
    public String toString() {
        return String.format("%s(condition='%s')", getClass().getSimpleName(), condition);
    }
}
