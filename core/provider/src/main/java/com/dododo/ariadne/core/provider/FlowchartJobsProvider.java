package com.dododo.ariadne.core.provider;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.job.AbstractJob;

import java.util.List;

public abstract class FlowchartJobsProvider {

    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void populateJobs(List<AbstractJob> jobs);
}
