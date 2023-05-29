package com.dododo.ariadne.renpy.provider;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.renpy.job.CollectStatesJob;
import com.dododo.ariadne.renpy.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.renpy.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.renpy.job.RemoveComplexStatesJob;
import com.dododo.ariadne.renpy.job.PrepareGroupCallStatesJob;
import com.dododo.ariadne.renpy.job.JoinLinkJumpPointsJob;
import com.dododo.ariadne.renpy.job.JoinStatesJob;
import com.dododo.ariadne.renpy.job.PrepareJaxbComplexSwitchStatesJob;
import com.dododo.ariadne.renpy.job.RemoveJumpToPointRemaindersJob;
import com.dododo.ariadne.renpy.job.RemoveSkipComplexStatesJob;
import com.dododo.ariadne.renpy.job.RenPyRemoveStateJob;
import com.dododo.ariadne.renpy.job.RemoveUnknownGroupCallStatesJob;
import com.dododo.ariadne.renpy.job.ValidateRawFlowchartJob;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;

import java.util.List;
import java.util.stream.IntStream;

public final class RenPyFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        JaxbComplexState rootState = new JaxbGroupState();

        addCollectStatesJob(jobs, rootState);
        jobs.add(new ValidateRawFlowchartJob(rootState));
        jobs.add(new RemoveSkipComplexStatesJob(rootState));
        jobs.add(new PrepareJaxbComplexSwitchStatesJob(rootState));
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob(rootState));
        jobs.add(new JoinStatesJob(rootState));

        jobs.add(new RemoveComplexStatesJob());
        jobs.add(new JoinLinkJumpPointsJob());
        jobs.add(new PrepareGroupCallStatesJob());
        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new RemoveUnknownGroupCallStatesJob());
        jobs.add(new RenPyRemoveStateJob<>(LabelledGroup.class));
        jobs.add(new RenPyRemoveStateJob<>(CallToState.class));
        jobs.add(new RenPyRemoveStateJob<>(PassState.class));
        jobs.add(new RemoveJumpToPointRemaindersJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, JaxbComplexState rootState) {
        List<String> inputFiles = configuration.getInputFiles();
        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new CollectStatesJob(index, rootState)).forEach(jobs::add);
    }
}
