package com.dododo.ariadne.xml.contract;

import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

public abstract class XmlSimpleFlowchartContract extends SimpleFlowchartContract
        implements XmlFlowchartContract {

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
