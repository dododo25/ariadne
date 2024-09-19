package com.dododo.ariadne.xml.provider;

import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.job.PrepareComplexStateEntryFlowchartJob;
import com.dododo.ariadne.xml.job.PrepareMenuStatesJob;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;
import com.dododo.ariadne.xml.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.xml.job.CollectStatesJob;
import com.dododo.ariadne.xml.job.JoinMarkerWithGoToPointsJob;
import com.dododo.ariadne.xml.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.xml.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.xml.job.RemoveComplexStatesJob;
import com.dododo.ariadne.xml.job.RemoveExcludedStatesJob;
import com.dododo.ariadne.xml.job.RemoveGoToPointRemaindersJob;
import com.dododo.ariadne.xml.job.RemoveStateJob;

import java.util.List;
import java.util.stream.IntStream;

public class XmlFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        jobs.add(new PrepareComplexStateEntryFlowchartJob());

        addCollectStatesJob(jobs);

        jobs.add(new RemoveExcludedStatesJob());
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob());

        jobs.add(new PrepareMenuStatesJob());
        jobs.add(new PrepareSwitchStatesJob());

        jobs.add(new JoinMarkerWithGoToPointsJob());
        jobs.add(new RemoveComplexStatesJob());
        jobs.add(new RemoveStateJob<>(Marker.class));
        jobs.add(new RemoveStateJob<>(PassState.class));
        jobs.add(new RemoveGoToPointRemaindersJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs) {
        List<String> inputFiles = configuration.getInputFiles();

        IntStream.range(0, inputFiles.size())
                .mapToObj(CollectStatesJob::new).forEach(jobs::add);
    }
}
