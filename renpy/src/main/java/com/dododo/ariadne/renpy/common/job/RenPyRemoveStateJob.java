package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RenPyRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public RenPyRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected ParentFirstFlowchartMouse prepareMouse() {
        return new ParentFirstRenPyFlowchartMouse();
    }

    @Override
    protected void process(T state) {
        RenPyStateManipulatorUtil.replace(state, state.getNext());
    }
}
