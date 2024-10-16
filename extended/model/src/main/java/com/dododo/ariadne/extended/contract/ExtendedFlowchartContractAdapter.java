package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.RootComplexState;

public abstract class ExtendedFlowchartContractAdapter extends FlowchartContractAdapter
        implements ExtendedFlowchartContract {

    @Override
    public void accept(RootComplexState rootComplexState) {}

    @Override
    public void accept(PassState state) {}

    @Override
    public void accept(Marker marker) {}

    @Override
    public void accept(GoToPoint point) {}

    @Override
    public void accept(ComplexMenu complexMenu) {}

    @Override
    public void accept(ComplexOption complexOption) {}

    @Override
    public void accept(ComplexSwitch complexSwitch) {}

    @Override
    public void accept(ComplexSwitchBranch branch) {}
}
