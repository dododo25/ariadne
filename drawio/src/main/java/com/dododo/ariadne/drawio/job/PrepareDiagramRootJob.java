package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.mouse.BlockFlowchartMouse;
import com.dododo.ariadne.mxg.common.mouse.ParentFirstBlockFlowchartMouse;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxFile;

import java.util.concurrent.atomic.AtomicReference;

public final class PrepareDiagramRootJob extends DrawIoAbstractJob {

    public PrepareDiagramRootJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        DiagramRoot diagramRoot = getMxFile().getDiagram().getModel().getRoot();
        BlockFlowchartMouse mouse = new ParentFirstBlockFlowchartMouse();

        mouse.accept(getRootBlock(), block -> block.accept(diagramRoot));
    }
}
