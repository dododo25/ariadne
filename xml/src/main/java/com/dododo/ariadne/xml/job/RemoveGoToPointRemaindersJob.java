package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.ParentFirstXmlFlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveGoToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(GoToPoint point) {
                XmlStateManipulatorUtil.replace(point, new EndPoint());
            }
        };
        FlowchartMouse mouse = new ParentFirstXmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
