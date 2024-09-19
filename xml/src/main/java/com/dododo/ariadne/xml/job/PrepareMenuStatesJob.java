package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.util.XmlFlowchartManipulatorUtil;

public final class PrepareMenuStatesJob extends AbstractJob {

    private final StateCollector<ChainState> collector;

    public PrepareMenuStatesJob() {
        this.collector = new LeafChainStateCollector(new XmlFlowchartMouse());
    }

    @Override
    public void run() {
        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(ComplexMenu complexMenu) {
                Menu menu = new Menu();

                complexMenu.childrenStream().map(ComplexOption.class::cast).forEach(complexOption -> {
                    Option option = complexOption.getCondition() == null
                            ? new Option(complexOption.getValue())
                            : new ConditionalOption(complexOption.getValue(), complexOption.getCondition());

                    State firstChild = complexOption.childAt(0);

                    option.setNext(firstChild);
                    firstChild.removeRoot(complexOption);

                    for (int i = 0; i < complexOption.childrenCount() - 1; i++) {
                        State child = complexOption.childAt(i);
                        State nextChild = complexOption.childAt(i + 1);

                        collector.collect(child)
                                .forEach(leaf -> leaf.setNext(nextChild));

                        nextChild.removeRoot(complexOption);
                    }

                    menu.addBranch(option);
                });

                XmlFlowchartManipulatorUtil.replace(complexMenu, menu);
            }
        };
        FlowchartMouse mouse = new XmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
