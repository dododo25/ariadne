package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.GenericFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.RootComplexState;

public abstract class ExtendedGenericFlowchartContract extends GenericFlowchartContract
        implements ExtendedFlowchartContract {

    @Override
    public final void accept(RootComplexState rootComplexState) {
        acceptComplexState(rootComplexState);
    }

    @Override
    public final void accept(ComplexMenu complexMenu) {
        acceptComplexState(complexMenu);
    }

    @Override
    public final void accept(ComplexOption complexOption) {
        acceptComplexState(complexOption);
    }

    @Override
    public final void accept(ComplexSwitch complexSwitch) {
        acceptComplexState(complexSwitch);
    }

    @Override
    public final void accept(ComplexSwitchBranch switchBranch) {
        acceptComplexState(switchBranch);
    }

    @Override
    public final void accept(PassState state) {
        acceptChainState(state);
    }

    @Override
    public final void accept(Marker marker) {
        acceptChainState(marker);
    }

    @Override
    public void accept(GoToPoint point) {}

    public void acceptComplexState(ComplexState complexState) {}
}
