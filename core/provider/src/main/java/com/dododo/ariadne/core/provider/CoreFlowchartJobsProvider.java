package com.dododo.ariadne.core.provider;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.job.OptimizeFlowchartJob;
import com.dododo.ariadne.core.job.PrepareEndStateJob;
import com.dododo.ariadne.core.job.RemoveEndPointDuplicatesJob;
import com.dododo.ariadne.core.job.RemoveStateDuplicatesJob;

import java.util.List;

public class CoreFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        jobs.add(new PrepareEndStateJob());
        jobs.add(new RemoveStateDuplicatesJob());
        jobs.add(new RemoveEndPointDuplicatesJob());
        jobs.add(new OptimizeFlowchartJob());
    }
}
