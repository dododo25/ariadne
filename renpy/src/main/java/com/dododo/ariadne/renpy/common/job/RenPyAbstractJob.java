package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.renpy.common.util.RenPyFlowchartTreeUtil;

public abstract class RenPyAbstractJob extends AbstractJob {

    @Override
    protected FlowchartContractFactory selectFactoryBasedOnFlowchartTreeSize(
            FlowchartContractFactory f1,
            FlowchartContractFactory f2) {
        if (!getConfiguration().isLarge()) {
            return f2;
        }

        return RenPyFlowchartTreeUtil.isLarge(getFlowchart()) ? f1 : f2;
    }
}
