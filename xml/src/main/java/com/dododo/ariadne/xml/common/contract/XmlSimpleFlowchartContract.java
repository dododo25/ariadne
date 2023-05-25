package com.dododo.ariadne.xml.common.contract;

import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;

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
    public final void accept(ComplexSwitch state) {
        acceptState(state);
    }

    @Override
    public final void accept(SwitchBranch state) {
        acceptState(state);
    }

    @Override
    public void accept(Marker marker) {
        acceptState(marker);
    }

    @Override
    public void accept(GoToPoint point) {
        acceptState(point);
    }
}
