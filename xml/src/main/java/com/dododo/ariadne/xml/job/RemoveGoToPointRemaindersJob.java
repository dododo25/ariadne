package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedFlowchartManipulatorUtil;
import com.dododo.ariadne.extended.model.GoToPoint;

public final class RemoveGoToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new ExtendedFlowchartContractAdapter() {
            @Override
            public void accept(GoToPoint point) {
                ExtendedFlowchartManipulatorUtil.replace(point, new EndPoint());
            }
        };
        FlowchartMouse mouse = new ExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
