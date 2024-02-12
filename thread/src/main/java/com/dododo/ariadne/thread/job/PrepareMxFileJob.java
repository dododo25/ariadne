package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.mxg.model.Diagram;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxFile;
import com.dododo.ariadne.mxg.model.MxGraphModel;

import java.util.concurrent.atomic.AtomicReference;

public final class PrepareMxFileJob extends ThreadAbstractJob {

    public PrepareMxFileJob(AtomicReference<MxFile> mxFileRef,
                            AtomicReference<JaxbState> jaxbStateRef,
                            AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
    }

    @Override
    public void run() {
        MxFile mxFile = MxFile.createDefault();
        MxGraphModel model = MxGraphModel.createDefault();
        DiagramRoot root = DiagramRoot.createDefault();

        Diagram diagram = new Diagram();
        diagram.setName("Page #1");

        model.setRoot(root);
        diagram.setModel(model);
        mxFile.setDiagram(diagram);

        setMxFile(mxFile);
    }
}
