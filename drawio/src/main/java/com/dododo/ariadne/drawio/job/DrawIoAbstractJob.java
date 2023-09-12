package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.mxg.MxFile;
import com.dododo.ariadne.common.job.AbstractJob;

import java.util.concurrent.atomic.AtomicReference;

public abstract class DrawIoAbstractJob extends AbstractJob {

    private final AtomicReference<MxFile> mxFileRef;

    private final AtomicReference<Block> rootBlockRef;

    protected DrawIoAbstractJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        this.mxFileRef = mxFileRef;
        this.rootBlockRef = rootBlockRef;
    }

    public MxFile getMxFile() {
        return mxFileRef.get();
    }

    public void setMxFile(MxFile file) {
        mxFileRef.set(file);
    }

    public Block getRootBlock() {
        return rootBlockRef.get();
    }

    public void setRootBlock(Block root) {
        rootBlockRef.set(root);
    }
}
