package com.dododo.ariadne.renpy.provider;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.provider.FlowchartJobsProvider;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.renpy.job.UnityCollectStatesJob;
import com.dododo.ariadne.renpy.job.JoinLabelWithJumpToPointsJob;
import com.dododo.ariadne.renpy.job.LoadLinesJob;
import com.dododo.ariadne.renpy.job.PrepareComplexStateEntryFlowchartJob;
import com.dododo.ariadne.renpy.job.PrepareComplexSwitchStatesJob;
import com.dododo.ariadne.renpy.job.PrepareLinesJob;
import com.dododo.ariadne.renpy.job.PrepareMenuStatesJob;
import com.dododo.ariadne.renpy.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.renpy.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.renpy.job.RearrangeVariableGroupStatesJob;
import com.dododo.ariadne.renpy.job.RemoveGoToPointRemaindersJob;
import com.dododo.ariadne.renpy.job.RemoveSkipComplexStatesJob;
import com.dododo.ariadne.renpy.job.RenPyRemoveStateJob;
import com.dododo.ariadne.renpy.job.ReplaceLabelledGroupsJob;
import com.dododo.ariadne.renpy.job.ValidateRawFlowchartJob;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class UnityFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        List<String> lines = new ArrayList<>();

        jobs.add(new PrepareComplexStateEntryFlowchartJob());

        addCollectStatesJob(jobs, lines);
        jobs.add(new PrepareLinesJob(lines));
        jobs.add(new UnityCollectStatesJob(lines));
        jobs.add(new ValidateRawFlowchartJob());
        jobs.add(new RemoveSkipComplexStatesJob());
        jobs.add(new PrepareComplexSwitchStatesJob());
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob());

        jobs.add(new RearrangeVariableGroupStatesJob());
        jobs.add(new PrepareMenuStatesJob());
        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new ReplaceLabelledGroupsJob());

        jobs.add(new JoinLabelWithJumpToPointsJob());
        jobs.add(new PrepareSingleEntryFlowchartJob());
        jobs.add(new RenPyRemoveStateJob<>(Marker.class));
        jobs.add(new RenPyRemoveStateJob<>(PassState.class));
        jobs.add(new RemoveGoToPointRemaindersJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, List<String> lines) {
        List<String> inputFiles = configuration.getInputFiles();

        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new LoadLinesJob(index, lines)).forEach(jobs::add);
    }
}
