package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedFlowchartManipulatorUtil;

public final class XmlRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public XmlRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected FlowchartMouse prepareMouse() {
        return new ExtendedFlowchartMouse();
    }

    @Override
    protected void process(T state) {
        ExtendedFlowchartManipulatorUtil.replace(state, state.getNext());
    }
}
