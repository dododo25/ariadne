package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;

import java.util.List;

public final class PrepareLinesJob extends AbstractJob {

    private final List<String> lines;

    public PrepareLinesJob(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void run() {
        replaceParentheses();
        groupLines();
    }

    private void replaceParentheses() {
        for(int i = 0; i < lines.size(); i++) {
            String prepared = lines.get(i);

            while (prepared.matches("^(.*)[{}]\\s*$")) {
                if (prepared.matches("^(.*)\\{\\s*$")) {
                    prepared = prepared.replaceAll("^(.*)\\{\\s*$", "$1:");
                } else {
                    prepared = prepared.replaceAll("^(.*)}\\s*$", "$1");
                }
            }

            lines.set(i, prepared);
        }
    }

    private void groupLines() {
        int i = 0;

        while (i < lines.size()) {
            String line = lines.get(i);

            if (line.matches("^\\s*:$")) {
                String prevLine = lines.get(i - 1);
                lines.set(i - 1, prevLine + ":");
                lines.remove(i);
            } else {
                i++;
            }
        }
    }
}
