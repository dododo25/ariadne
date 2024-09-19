package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.util.XmlFlowchartManipulatorUtil;

public final class RemoveGoToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(GoToPoint point) {
                XmlFlowchartManipulatorUtil.replace(point, new EndPoint());
            }
        };
        FlowchartMouse mouse = new XmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
