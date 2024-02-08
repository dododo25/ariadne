package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;

public abstract class ExtendedSimpleFlowchartContract extends SimpleFlowchartContract
        implements ExtendedFlowchartContract {

    @Override
    public final void accept(ComplexState state) {
        acceptState(state);
    }

    @Override
    public final void accept(PassState state) {
        acceptState(state);
    }

    @Override
    public final void accept(Label group) {
        acceptState(group);
    }

    @Override
    public final void accept(GoToPoint point) {
        acceptState(point);
    }

    @Override
    public final void accept(ComplexSwitch complexSwitch) {
        acceptState(complexSwitch);
    }

    @Override
    public final void accept(SwitchBranch branch) {
        acceptState(branch);
    }
}
