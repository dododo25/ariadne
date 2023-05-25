package com.dododo.ariadne.xml.provider;

import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.xml.job.CollectStatesJob;
import com.dododo.ariadne.xml.job.JoinGoToPointsJob;
import com.dododo.ariadne.xml.job.JoinStatesJob;
import com.dododo.ariadne.xml.job.OptimizeFlowchartJob;
import com.dododo.ariadne.xml.job.PrepareEndStateJob;
import com.dododo.ariadne.xml.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.xml.job.RemoveEndPointDuplicatesJob;
import com.dododo.ariadne.xml.job.RemoveStateDuplicatesJob;
import com.dododo.ariadne.xml.job.RemoveStateJob;
import com.dododo.ariadne.xml.common.model.Marker;

import java.util.List;
import java.util.stream.IntStream;

public class XmlFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        JaxbRootState rootState = new JaxbRootState();

        addCollectStatesJob(jobs, rootState);
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob(rootState));
        jobs.add(new JoinStatesJob(rootState));

        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new JoinGoToPointsJob());
        jobs.add(new RemoveStateJob<>(Marker.class));
        jobs.add(new RemoveStateJob<>(PassState.class));
        jobs.add(new PrepareEndStateJob());
        jobs.add(new RemoveStateDuplicatesJob());
        jobs.add(new RemoveEndPointDuplicatesJob());
        jobs.add(new OptimizeFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, JaxbRootState rootState) {
        List<String> inputFiles = configuration.getInputFiles();
        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new CollectStatesJob(rootState, index)).forEach(jobs::add);
    }
}
