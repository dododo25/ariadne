package com.dododo.ariadne.renpy.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;

import java.util.Objects;

public class SwitchBranch extends SimpleState {

    public SwitchBranch() {
        super();
    }

    public SwitchBranch(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof SwitchBranch
                && Objects.equals(value, ((SwitchBranch) o).value) ? 0 : 1;
    }
}
