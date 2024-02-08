package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.extended.mouse.ParentFirstExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedStateManipulatorUtil;

public final class XmlRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public XmlRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected ParentFirstFlowchartMouse prepareMouse() {
        return new ParentFirstExtendedFlowchartMouse();
    }

    @Override
    protected void process(T state) {
        ExtendedStateManipulatorUtil.replace(state, state.getNext());
    }
}
