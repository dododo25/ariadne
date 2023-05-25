package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoSimpleFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mxg.DiagramRoot;
import com.dododo.ariadne.drawio.mxg.MxFile;

import java.util.concurrent.atomic.AtomicReference;

public final class PrepareDiagramRootJob extends DrawIoAbstractJob {

    public PrepareDiagramRootJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        DiagramRoot diagramRoot = getMxFile().getDiagram().getModel().getRoot();

        DrawIoFlowchartContract callback = new DrawIoSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                block.accept(diagramRoot);
            }
        };
        DrawIoFlowchartMouse mouse = new DrawIoFlowchartMouse(callback,
                new DrawIoParentFirstFlowchartMouseStrategy());

        getRootBlock().accept(mouse);
    }
}
