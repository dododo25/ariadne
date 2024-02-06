package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.renpy.common.composer.ParentFirstRenPyLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.util.RenPyFlowchartTreeUtil;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RenPyRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public RenPyRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected FlowchartContractComposer prepareComposer() {
        return RenPyFlowchartTreeUtil.isLarge(getFlowchart())
                ? new ParentFirstRenPyLargeTreeFlowchartContractComposer()
                : new RenPyFlowchartContractComposer();
    }

    @Override
    protected void process(T state) {
        RenPyStateManipulatorUtil.replace(state, state.getNext());
    }
}
