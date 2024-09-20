package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJob {

    protected final String name;

    protected final Logger log;

    private State flowchart;

    private Configuration configuration;

    protected AbstractJob() {
        this.name = getClass().getSimpleName();
        this.log = LoggerFactory.getLogger(getClass());
    }

    protected AbstractJob(String name) {
        this.name = name;
        this.log = LoggerFactory.getLogger(getClass());
    }

    public abstract void run();

    public String getName() {
        return name;
    }

    public State getFlowchart() {
        return flowchart;
    }

    public void setFlowchart(State flowchart) {
        this.flowchart = flowchart;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
