package com.dododo.ariadne.extended.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;

public final class ComplexSwitchBranch extends ComplexState {

    private final String value;

    private final boolean falseBranch;

    public ComplexSwitchBranch(boolean falseBranch) {
        this(null, falseBranch);
    }

    public ComplexSwitchBranch(String value, boolean falseBranch) {
        super();
        this.value = value;
        this.falseBranch = falseBranch;
    }

    public String getValue() {
        return value;
    }

    public boolean isFalseBranch() {
        return falseBranch;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((ExtendedFlowchartContract) contract).accept(this);
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value=null, falseBranch=%s)", getClass().getSimpleName(), falseBranch);
        }

        return String.format("%s(value='%s', falseBranch=%s)", getClass().getSimpleName(), value, falseBranch);
    }
}
