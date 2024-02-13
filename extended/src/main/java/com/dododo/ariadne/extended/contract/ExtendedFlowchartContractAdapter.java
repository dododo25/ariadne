package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;

public abstract class ExtendedFlowchartContractAdapter extends FlowchartContractAdapter
        implements ExtendedFlowchartContract {

    @Override
    public void accept(ComplexState state) {}

    @Override
    public void accept(PassState state) {}

    @Override
    public void accept(Label label) {}

    @Override
    public void accept(GoToPoint point) {}

    @Override
    public void accept(ComplexSwitch complexSwitch) {}

    @Override
    public void accept(SwitchBranch branch) {}
}
