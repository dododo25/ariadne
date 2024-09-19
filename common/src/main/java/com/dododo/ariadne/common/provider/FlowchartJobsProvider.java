package com.dododo.ariadne.common.provider;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.common.job.AbstractJob;

import java.util.List;

public abstract class FlowchartJobsProvider {

    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void populateJobs(List<AbstractJob> jobs);
}
