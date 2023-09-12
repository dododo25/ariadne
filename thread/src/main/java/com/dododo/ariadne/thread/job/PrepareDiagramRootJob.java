package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.contract.ThreadSimpleFlowchartContract;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import com.dododo.ariadne.thread.mouse.strategy.ThreadParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.mxg.DiagramRoot;
import com.dododo.ariadne.mxg.MxFile;

import java.util.concurrent.atomic.AtomicReference;

public final class PrepareDiagramRootJob extends ThreadAbstractJob {

    public PrepareDiagramRootJob(AtomicReference<MxFile> mxFileRef,
                                 AtomicReference<JaxbState> jaxbStateRef,
                                 AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
    }

    @Override
    public void run() {
        DiagramRoot diagramRoot = getMxFile().getDiagram().getModel().getRoot();

        ThreadFlowchartContract callback = new ThreadSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                block.accept(diagramRoot);
            }
        };
        ThreadFlowchartMouse mouse = new ThreadFlowchartMouse(callback,
                new ThreadParentFirstFlowchartMouseStrategy());

        getRootBlock().accept(mouse);
    }
}
