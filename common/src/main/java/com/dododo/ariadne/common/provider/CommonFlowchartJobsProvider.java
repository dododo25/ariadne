package com.dododo.ariadne.common.provider;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.common.job.OptimizeFlowchartJob;
import com.dododo.ariadne.common.job.PrepareEndStateJob;
import com.dododo.ariadne.common.job.RemoveEndPointDuplicatesJob;
import com.dododo.ariadne.common.job.RemoveStateDuplicatesJob;

import java.util.List;

public class CommonFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        jobs.add(new PrepareEndStateJob());
        jobs.add(new RemoveStateDuplicatesJob());
        jobs.add(new RemoveEndPointDuplicatesJob());
        jobs.add(new OptimizeFlowchartJob());
    }
}
