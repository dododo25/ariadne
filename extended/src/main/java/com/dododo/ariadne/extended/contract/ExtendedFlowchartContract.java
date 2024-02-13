package com.dododo.ariadne.extended.contract;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;

public interface ExtendedFlowchartContract extends FlowchartContract {

    void accept(ComplexState state);

    void accept(Label label);

    void accept(PassState state);

    void accept(ComplexSwitch complexSwitch);

    void accept(SwitchBranch branch);

    void accept(GoToPoint point);
}
