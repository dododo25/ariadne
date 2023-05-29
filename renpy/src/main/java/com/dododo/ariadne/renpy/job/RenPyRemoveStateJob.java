package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RenPyRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public RenPyRemoveStateJob(Class<T> type) {
        super(type, new RenPyFlowchartMouseFactory());
    }

    @Override
    protected void process(T state) {
        RenPyStateManipulatorUtil.replace(state, state.getNext());
    }
}
