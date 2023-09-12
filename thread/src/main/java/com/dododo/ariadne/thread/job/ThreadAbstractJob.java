package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.mxg.MxFile;
import com.dododo.ariadne.common.job.AbstractJob;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ThreadAbstractJob extends AbstractJob {

    private final AtomicReference<MxFile> mxFileRef;

    private final AtomicReference<JaxbState> jaxbStateRef;

    private final AtomicReference<Block> rootBlockRef;

    protected ThreadAbstractJob(AtomicReference<MxFile> mxFileRef,
                                AtomicReference<JaxbState> jaxbStateRef,
                                AtomicReference<Block> rootBlockRef) {
        this.mxFileRef = mxFileRef;
        this.jaxbStateRef = jaxbStateRef;
        this.rootBlockRef = rootBlockRef;
    }

    public MxFile getMxFile() {
        return mxFileRef.get();
    }

    public void setMxFile(MxFile file) {
        mxFileRef.set(file);
    }

    public JaxbState getJaxbState() {
        return jaxbStateRef.get();
    }

    public void setJaxbState(JaxbState state) {
        jaxbStateRef.set(state);
    }

    public Block getRootBlock() {
        return rootBlockRef.get();
    }

    public void setRootBlock(Block root) {
        rootBlockRef.set(root);
    }
}
