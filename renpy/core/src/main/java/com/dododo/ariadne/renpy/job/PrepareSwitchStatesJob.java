package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

import java.util.function.Consumer;

public final class PrepareSwitchStatesJob extends RemoveComplexStatesJob {

    private final StateCollector<ComplexSwitch> complexSwitchStateCollector;

    public PrepareSwitchStatesJob() {
        super();
        this.complexSwitchStateCollector = new GenericStateCollector<>(new RenPyFlowchartMouse(), ComplexSwitch.class);
    }

    @Override
    public void run() {
        complexSwitchStateCollector.collect(getFlowchart()).forEach(complexSwitch -> {
            ComplexSwitchBranch branchState = (ComplexSwitchBranch) complexSwitch.childAt(0);

            Switch rootSwitch = new Switch(branchState.getValue());
            Switch current = rootSwitch;

            prepareBranch(branchState, current::setTrueBranch);

            for (int i = 1; i < complexSwitch.childrenCount(); i++) {
                branchState = (ComplexSwitchBranch) complexSwitch.childAt(i);

                if (i < complexSwitch.childrenCount() - 1) {
                    Switch falseSwitch = new Switch(branchState.getValue());

                    prepareBranch(branchState, falseSwitch::setTrueBranch);

                    current.setFalseBranch(falseSwitch);
                    current = falseSwitch;
                } else {
                    prepareBranch(branchState, current::setFalseBranch);
                }
            }

            RenPyFlowchartManipulatorUtil.replace(complexSwitch, rootSwitch);
        });
    }

    private void prepareBranch(ComplexSwitchBranch switchBranch, Consumer<State> consumer) {
        State firstState = switchBranch.childAt(0);

        joinChildren(switchBranch);

        firstState.removeRoot(switchBranch);
        consumer.accept(firstState);
    }
}
