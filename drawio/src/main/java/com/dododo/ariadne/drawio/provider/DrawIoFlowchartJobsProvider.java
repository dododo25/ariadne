package com.dododo.ariadne.drawio.provider;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.provider.FlowchartJobsProvider;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.job.ApplyLayoutJob;
import com.dododo.ariadne.drawio.job.PrepareDiagramRootJob;
import com.dododo.ariadne.drawio.job.PrepareMxFileJob;
import com.dododo.ariadne.drawio.job.PrepareRootBlockJob;
import com.dododo.ariadne.drawio.job.SaveFlowchartJob;
import com.dododo.ariadne.mxg.model.MxFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class DrawIoFlowchartJobsProvider extends FlowchartJobsProvider {
    
    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        AtomicReference<MxFile> mxFileRef = new AtomicReference<>();
        AtomicReference<Block> blockRef = new AtomicReference<>();

        jobs.add(new PrepareMxFileJob(mxFileRef, blockRef));
        jobs.add(new PrepareRootBlockJob(mxFileRef, blockRef));
        jobs.add(new ApplyLayoutJob(mxFileRef, blockRef));
        jobs.add(new PrepareDiagramRootJob(mxFileRef, blockRef));
        jobs.add(new SaveFlowchartJob(mxFileRef, blockRef));
    }
}
