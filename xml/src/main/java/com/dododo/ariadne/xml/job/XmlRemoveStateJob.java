package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.RemoveStateJob;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.xml.common.mouse.ParentFirstXmlFlowchartMouse;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class XmlRemoveStateJob<T extends ChainState> extends RemoveStateJob<T> {

    public XmlRemoveStateJob(Class<T> type) {
        super(type);
    }

    @Override
    protected ParentFirstFlowchartMouse prepareMouse() {
        return new ParentFirstXmlFlowchartMouse();
    }

    @Override
    protected void process(T state) {
        XmlStateManipulatorUtil.replace(state, state.getNext());
    }
}
