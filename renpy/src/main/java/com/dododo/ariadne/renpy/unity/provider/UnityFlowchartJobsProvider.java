package com.dododo.ariadne.renpy.unity.provider;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.common.provider.FlowchartJobsProvider;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.renpy.common.job.AddMissingSwitchFalseBranchComplexStateJob;
import com.dododo.ariadne.renpy.common.job.JoinLinkJumpPointsJob;
import com.dododo.ariadne.renpy.common.job.JoinStatesJob;
import com.dododo.ariadne.renpy.common.job.PrepareJaxbComplexSwitchStatesJob;
import com.dododo.ariadne.renpy.common.job.PrepareSingleEntryFlowchartJob;
import com.dododo.ariadne.renpy.common.job.PrepareSwitchStatesJob;
import com.dododo.ariadne.renpy.common.job.RemoveComplexStatesJob;
import com.dododo.ariadne.renpy.common.job.RemoveJumpToPointRemaindersJob;
import com.dododo.ariadne.renpy.common.job.RemoveSkipComplexStatesJob;
import com.dododo.ariadne.renpy.common.job.RenPyRemoveStateJob;
import com.dododo.ariadne.renpy.common.job.ValidateRawFlowchartJob;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.unity.job.CollectStatesJob;
import com.dododo.ariadne.renpy.unity.job.LoadLinesJob;
import com.dododo.ariadne.renpy.unity.job.PrepareLinesJob;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class UnityFlowchartJobsProvider extends FlowchartJobsProvider {

    @Override
    public void populateJobs(List<AbstractJob> jobs) {
        List<String> lines = new ArrayList<>();
        JaxbComplexState rootState = new JaxbRootState();

        addCollectStatesJob(jobs, lines);
        jobs.add(new PrepareLinesJob(lines));
        jobs.add(new CollectStatesJob(rootState, lines));
        jobs.add(new ValidateRawFlowchartJob(rootState));
        jobs.add(new RemoveSkipComplexStatesJob(rootState));
        jobs.add(new PrepareJaxbComplexSwitchStatesJob(rootState));
        jobs.add(new AddMissingSwitchFalseBranchComplexStateJob(rootState));
        jobs.add(new JoinStatesJob(rootState));

        jobs.add(new PrepareSwitchStatesJob());
        jobs.add(new JoinLinkJumpPointsJob());
        jobs.add(new RemoveComplexStatesJob());
        jobs.add(new RemoveJumpToPointRemaindersJob());
        jobs.add(new RenPyRemoveStateJob<>(LabelledGroup.class));
        jobs.add(new RenPyRemoveStateJob<>(PassState.class));
        jobs.add(new PrepareSingleEntryFlowchartJob());
    }

    private void addCollectStatesJob(List<AbstractJob> jobs, List<String> lines) {
        List<String> inputFiles = configuration.getInputFiles();

        IntStream.range(0, inputFiles.size())
                .mapToObj(index -> new LoadLinesJob(index, lines)).forEach(jobs::add);
    }
}
