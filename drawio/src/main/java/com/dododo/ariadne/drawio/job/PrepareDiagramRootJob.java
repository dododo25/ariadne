package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.mouse.BlockFlowchartMouse;
import com.dododo.ariadne.block.mouse.strategy.ParentFirstBlockFlowchartMouseStrategy;
import com.dododo.ariadne.mxg.DiagramRoot;
import com.dododo.ariadne.mxg.MxFile;

import java.util.concurrent.atomic.AtomicReference;

public final class PrepareDiagramRootJob extends DrawIoAbstractJob {

    public PrepareDiagramRootJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        DiagramRoot diagramRoot = getMxFile().getDiagram().getModel().getRoot();

        BlockFlowchartContract callback = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                block.accept(diagramRoot);
            }
        };
        BlockFlowchartMouse mouse = new BlockFlowchartMouse(callback,
                new ParentFirstBlockFlowchartMouseStrategy());

        getRootBlock().accept(mouse);
    }
}
