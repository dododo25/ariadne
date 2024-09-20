package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;

public interface ExtendedFlowchartContract extends FlowchartContract {

    void accept(ComplexState state);

    void accept(Marker marker);

    void accept(PassState state);

    void accept(ComplexMenu complexMenu);

    void accept(ComplexOption complexOption);

    void accept(ComplexSwitch complexSwitch);

    void accept(ComplexSwitchBranch branch);

    void accept(GoToPoint point);
}
