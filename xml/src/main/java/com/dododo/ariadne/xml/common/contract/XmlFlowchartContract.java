package com.dododo.ariadne.xml.common.contract;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;

public interface XmlFlowchartContract extends FlowchartContract {

    void accept(ComplexState state);

    void accept(PassState state);

    void accept(ComplexSwitch state);

    void accept(SwitchBranch state);

    void accept(Marker marker);

    void accept(GoToPoint point);

}
