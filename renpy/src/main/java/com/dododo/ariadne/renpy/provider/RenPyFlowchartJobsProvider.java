package com.dododo.ariadne.renpy.provider;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.provider.FlowchartJobsProvider;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.renpy.job.JoinLabelWithJumpToPointsJob;
import com.dododo.ariadne.renpy.job.PrepareComplexStateEntryFlowchartJob;
import com.dododo.ariadne.renpy.job.PrepareComplexSwitchStatesJob;
import com.dododo.ariadne.renpy.job.PrepareMenuStatesJob;
import com.dododo.ariadne.renpy.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.renpy.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.renpy.job.RearrangeLabelledGroupsJob;
import com.dododo.ariadne.renpy.job.RemoveComplexStatesJob;
import com.dododo.ariadne.renpy.job.RemoveGoToPointRemaindersJob;
import com.dododo.ariadne.renpy.job.RemoveMarkerStateJob;
import com.dododo.ariadne.renpy.job.RemoveSkipComplexStatesJob;
import com.dododo.ariadne.renpy.job.RenPyRemoveStateJob;
import com.dododo.ariadne.renpy.job.ReplaceLabelledGroupsJob;
import com.dododo.ariadne.renpy.job.ValidateRawFlowchartJob;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.job.CollectStatesJob;
import com.dododo.ariadne.renpy.job.PrepareGroupCallStatesJob;
import com.dododo.ariadne.renpy.job.RearrangeVariableGroupStatesJob;
import com.dododo.ariadne.renpy.job.RemoveUnknownGroupCallStatesJob;

import java.util.List;
import java.util.stream.IntStream;

public final class RenPyFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        jobs.add(new PrepareComplexStateEntryFlowchartJob());

        addCollectStatesJob(jobs);

        jobs.add(new ValidateRawFlowchartJob());
        jobs.add(new RemoveSkipComplexStatesJob());
        jobs.add(new PrepareComplexSwitchStatesJob());
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob());
        jobs.add(new RearrangeVariableGroupStatesJob());
        jobs.add(new RearrangeLabelledGroupsJob());
        jobs.add(new PrepareMenuStatesJob());
        jobs.add(new PrepareSwitchStatesJob());

        jobs.add(new ReplaceLabelledGroupsJob());
        jobs.add(new RemoveComplexStatesJob());

        jobs.add(new JoinLabelWithJumpToPointsJob());
        jobs.add(new PrepareGroupCallStatesJob());
        jobs.add(new RemoveUnknownGroupCallStatesJob());
        jobs.add(new RemoveMarkerStateJob());

        jobs.add(new RenPyRemoveStateJob<>(CallToState.class));
        jobs.add(new RenPyRemoveStateJob<>(PassState.class));
        jobs.add(new RemoveGoToPointRemaindersJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs) {
        List<String> inputFiles = configuration.getInputFiles();

        IntStream.range(0, inputFiles.size())
                .mapToObj(CollectStatesJob::new)
                .forEach(jobs::add);
    }
}
