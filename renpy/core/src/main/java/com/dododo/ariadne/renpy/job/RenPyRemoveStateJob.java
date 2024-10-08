package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.RemoveStateJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class RenPyRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public RenPyRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected FlowchartMouse prepareMouse() {
        return new RenPyFlowchartMouse();
    }

    @Override
    protected void process(T state) {
        RenPyFlowchartManipulatorUtil.replace(state, state.getNext());
    }
}
