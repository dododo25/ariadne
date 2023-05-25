package com.dododo.ariadne.xml.common.contract;

import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;

public abstract class XmlFlowchartContractAdapter extends FlowchartContractAdapter
        implements XmlFlowchartContract {

    @Override
    public void accept(ComplexState state) {}

    @Override
    public void accept(PassState state) {}

    @Override
    public void accept(ComplexSwitch state) {}

    @Override
    public void accept(SwitchBranch state) {}

    @Override
    public void accept(Marker marker) {}

    @Override
    public void accept(GoToPoint point) {}
}
