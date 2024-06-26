package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.mouse.ParentFirstExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedStateManipulatorUtil;

public final class RemoveGoToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new ExtendedFlowchartContractAdapter() {
            @Override
            public void accept(GoToPoint point) {
                ExtendedStateManipulatorUtil.replace(point, new EndPoint());
            }
        };
        FlowchartMouse mouse = new ParentFirstExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
