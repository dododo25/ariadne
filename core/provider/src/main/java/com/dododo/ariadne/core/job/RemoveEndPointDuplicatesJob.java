package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.util.FlowchartManipulatorUtil;

public final class RemoveEndPointDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        EndPoint commonEndPoint = new EndPoint();

        FlowchartContract callback = new FlowchartContractAdapter() {
            @Override
            public void accept(EndPoint point) {
                FlowchartManipulatorUtil.replace(point, commonEndPoint);
            }
        };
        FlowchartMouse mouse = new FlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
