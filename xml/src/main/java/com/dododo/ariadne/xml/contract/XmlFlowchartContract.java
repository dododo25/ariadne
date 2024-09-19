package com.dododo.ariadne.xml.contract;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

public interface XmlFlowchartContract extends FlowchartContract {

    void accept(ComplexState state);

    void accept(Marker marker);

    void accept(PassState state);

    void accept(ComplexMenu complexMenu);

    void accept(ComplexOption complexOption);

    void accept(ComplexSwitch complexSwitch);

    void accept(ComplexSwitchBranch branch);

    void accept(GoToPoint point);
}
