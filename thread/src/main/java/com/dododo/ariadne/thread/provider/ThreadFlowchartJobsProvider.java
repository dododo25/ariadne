package com.dododo.ariadne.thread.provider;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.thread.job.ApplyLayoutJob;
import com.dododo.ariadne.thread.job.PrepareDiagramRootJob;
import com.dododo.ariadne.thread.job.PrepareJaxbStateJob;
import com.dododo.ariadne.thread.job.PrepareMxFileJob;
import com.dododo.ariadne.thread.job.PrepareRootBlockJob;
import com.dododo.ariadne.thread.job.SaveFlowchartJob;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.mxg.model.MxFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ThreadFlowchartJobsProvider extends FlowchartJobsProvider {
    
    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        AtomicReference<MxFile> mxFileRef = new AtomicReference<>();
        AtomicReference<JaxbState> jaxbStateRef = new AtomicReference<>();
        AtomicReference<Block> blockRef = new AtomicReference<>();

        jobs.add(new PrepareMxFileJob(mxFileRef, jaxbStateRef, blockRef));
        jobs.add(new PrepareRootBlockJob(mxFileRef, jaxbStateRef, blockRef));
        jobs.add(new ApplyLayoutJob(mxFileRef, jaxbStateRef, blockRef));
        jobs.add(new PrepareDiagramRootJob(mxFileRef, jaxbStateRef, blockRef));
        jobs.add(new PrepareJaxbStateJob(jaxbStateRef));
        jobs.add(new SaveFlowchartJob(mxFileRef, jaxbStateRef, blockRef));
    }
}
