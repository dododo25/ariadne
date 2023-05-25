package com.dododo.ariadne.xml.common.mouse;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.mouse.strategy.XmlFlowchartMouseStrategy;

public class XmlFlowchartMouse extends FlowchartMouse implements XmlFlowchartContract {

    public XmlFlowchartMouse(XmlFlowchartContract callback, XmlFlowchartMouseStrategy strategy) {
        super(callback, strategy);
    }

    @Override
    public void accept(ComplexState state) {
        ((XmlFlowchartMouseStrategy) strategy).acceptComplexState(state, this, callback, visited);
    }

    @Override
    public void accept(PassState state) {
        strategy.acceptChainState(state, this, callback, visited);
    }

    @Override
    public void accept(ComplexSwitch state) {
        ((XmlFlowchartMouseStrategy) strategy).acceptComplexState(state, this, callback, visited);
    }

    @Override
    public void accept(SwitchBranch state) {
        strategy.acceptChainState(state, this, callback, visited);
    }

    @Override
    public void accept(Marker marker) {
        strategy.acceptChainState(marker, this, callback, visited);
    }

    @Override
    public void accept(GoToPoint point) {
        acceptEndState(point);
    }
}
