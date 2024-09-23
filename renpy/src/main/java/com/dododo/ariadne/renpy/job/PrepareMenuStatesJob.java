package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class PrepareMenuStatesJob extends AbstractJob {

    private final StateCollector<ChainState> collector;

    public PrepareMenuStatesJob() {
        this.collector = new LeafChainStateCollector(new ExtendedFlowchartMouse());
    }

    @Override
    public void run() {
        FlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexMenu complexMenu) {
                Menu menu = new Menu();

                complexMenu.childrenStream().map(ComplexOption.class::cast).forEach(complexOption -> {
                    Option option = complexOption.getCondition() == null
                            ? new Option(complexOption.getValue())
                            : new ConditionalOption(complexOption.getValue(), complexOption.getCondition());

                    if (complexOption.childrenCount() > 0) {
                        State firstChild = complexOption.childAt(0);

                        option.setNext(firstChild);
                        firstChild.removeRoot(complexOption);
                    }

                    for (int i = 0; i < complexOption.childrenCount() - 1; i++) {
                        State child = complexOption.childAt(i);
                        State nextChild = complexOption.childAt(i + 1);

                        collector.collect(child)
                                .forEach(leaf -> leaf.setNext(nextChild));

                        nextChild.removeRoot(complexOption);
                    }

                    menu.addBranch(option);
                });

                 RenPyFlowchartManipulatorUtil.replace(complexMenu, menu);
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
