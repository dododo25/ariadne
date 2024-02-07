package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

public final class RemoveEndPointDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstFlowchartMouse();

        EndPoint commonEndPoint = new EndPoint();

        FlowchartContract callback = new FlowchartContractAdapter() {
            @Override
            public void accept(EndPoint point) {
                StateManipulatorUtil.replace(point, commonEndPoint);
            }
        };

        mouse.accept(getFlowchart(), callback);
    }
}
