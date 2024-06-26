package com.dododo.ariadne.renpy.rpy.provider;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.renpy.common.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.renpy.common.job.JoinLabelWithGoToPointsJob;
import com.dododo.ariadne.renpy.common.job.JoinStatesJob;
import com.dododo.ariadne.renpy.common.job.PrepareJaxbComplexSwitchStatesJob;
import com.dododo.ariadne.renpy.common.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.renpy.common.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.renpy.common.job.RearrangeLabelledGroupsJob;
import com.dododo.ariadne.renpy.common.job.RemoveComplexStatesJob;
import com.dododo.ariadne.renpy.common.job.RemoveGoToPointRemaindersJob;
import com.dododo.ariadne.renpy.common.job.RemoveSkipComplexStatesJob;
import com.dododo.ariadne.renpy.common.job.RenPyRemoveStateJob;
import com.dododo.ariadne.renpy.common.job.ValidateRawFlowchartJob;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.rpy.job.CollectStatesJob;
import com.dododo.ariadne.renpy.rpy.job.PrepareGroupCallStatesJob;
import com.dododo.ariadne.renpy.rpy.job.RearrangeInitGroupStatesJob;
import com.dododo.ariadne.renpy.rpy.job.RemoveUnknownGroupCallStatesJob;

import java.util.List;
import java.util.stream.IntStream;

public final class RenPyFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        JaxbRootState rootState = new JaxbRootState();

        addCollectStatesJob(jobs, rootState);
        jobs.add(new ValidateRawFlowchartJob(rootState));
        jobs.add(new RemoveSkipComplexStatesJob(rootState));
        jobs.add(new PrepareJaxbComplexSwitchStatesJob(rootState));
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob(rootState));
        jobs.add(new RearrangeInitGroupStatesJob(rootState));
        jobs.add(new RearrangeLabelledGroupsJob(rootState));
        jobs.add(new JoinStatesJob(rootState));

        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new JoinLabelWithGoToPointsJob());
        jobs.add(new PrepareGroupCallStatesJob());
        jobs.add(new RemoveComplexStatesJob());
        jobs.add(new RemoveUnknownGroupCallStatesJob());
        jobs.add(new RenPyRemoveStateJob<>(Label.class));
        jobs.add(new RenPyRemoveStateJob<>(CallToState.class));
        jobs.add(new RenPyRemoveStateJob<>(PassState.class));
        jobs.add(new RemoveGoToPointRemaindersJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, JaxbComplexState rootState) {
        List<String> inputFiles = configuration.getInputFiles();
        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new CollectStatesJob(index, rootState)).forEach(jobs::add);
    }
}
