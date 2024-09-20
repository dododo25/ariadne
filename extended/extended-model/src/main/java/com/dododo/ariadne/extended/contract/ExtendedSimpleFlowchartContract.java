package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;

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
    public final void accept(Marker marker) {
        acceptState(marker);
    }

    @Override
    public final void accept(GoToPoint point) {
        acceptState(point);
    }

    @Override
    public final void accept(ComplexMenu complexMenu) {
        acceptState(complexMenu);
    }

    @Override
    public final void accept(ComplexOption complexOption) {
        acceptState(complexOption);
    }

    @Override
    public final void accept(ComplexSwitch complexSwitch) {
        acceptState(complexSwitch);
    }

    @Override
    public final void accept(ComplexSwitchBranch switchBranch) {
        acceptState(switchBranch);
    }
}
