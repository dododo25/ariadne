package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.mouse.BlockFlowchartMouse;
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
        BlockFlowchartMouse mouse = new BlockFlowchartMouse();

        mouse.accept(getRootBlock(), block -> block.accept(diagramRoot));
    }
}
