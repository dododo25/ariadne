package com.dododo.ariadne.xml.contract;

import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

public abstract class XmlFlowchartContractAdapter extends FlowchartContractAdapter
        implements XmlFlowchartContract {

    @Override
    public void accept(ComplexState state) {}

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
