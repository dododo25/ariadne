package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.util.RenPyFlowchartTreeUtil;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RenPyRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public RenPyRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected FlowchartContractFactory prepareFactory() {
        return RenPyFlowchartTreeUtil.isLarge(getFlowchart())
                ? new ParentFirstRenPyLargeTreeFlowchartContractFactory()
                : new RenPyFlowchartContractFactory();
    }

    @Override
    protected void process(T state) {
        RenPyStateManipulatorUtil.replace(state, state.getNext());
    }
}
