package com.dododo.ariadne.xml.provider;

import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.xml.job.CollectStatesJob;
import com.dododo.ariadne.xml.job.JoinGoToPointsJob;
import com.dododo.ariadne.xml.job.JoinStatesJob;
import com.dododo.ariadne.xml.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.xml.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.xml.job.RemoveComplexStatesJob;
import com.dododo.ariadne.xml.job.RemoveExcludedStatesJob;
import com.dododo.ariadne.xml.job.RemoveGoToPointRemaindersJob;
import com.dododo.ariadne.xml.job.XmlRemoveStateJob;
import com.dododo.ariadne.xml.common.model.Marker;

import java.util.List;
import java.util.stream.IntStream;

public class XmlFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        JaxbRootState rootState = new JaxbRootState();

        addCollectStatesJob(jobs, rootState);
        jobs.add(new RemoveExcludedStatesJob(rootState));
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob(rootState));
        jobs.add(new JoinStatesJob(rootState));

        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new JoinGoToPointsJob());
        jobs.add(new RemoveComplexStatesJob());
        jobs.add(new XmlRemoveStateJob<>(Marker.class));
        jobs.add(new XmlRemoveStateJob<>(PassState.class));
        jobs.add(new RemoveGoToPointRemaindersJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, JaxbRootState rootState) {
        List<String> inputFiles = configuration.getInputFiles();
        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new CollectStatesJob(rootState, index)).forEach(jobs::add);
    }
}
