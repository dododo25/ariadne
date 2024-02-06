package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.renpy.common.util.RenPyFlowchartTreeUtil;

public abstract class RenPyAbstractJob extends AbstractJob {

    @Override
    protected FlowchartContractComposer selectComposerBasedOnFlowchartTreeSize(
            FlowchartContractComposer c1,
            FlowchartContractComposer c2) {
        if (!getConfiguration().isLarge()) {
            return c2;
        }

        return RenPyFlowchartTreeUtil.isLarge(getFlowchart()) ? c1 : c2;
    }
}
