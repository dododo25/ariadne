package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class PrepareMenuStatesJob extends RemoveComplexStatesJob {

    private final StateCollector<ComplexMenu> complexMenuStateCollector;

    public PrepareMenuStatesJob() {
        super();
        this.complexMenuStateCollector = new GenericStateCollector<>(new RenPyFlowchartMouse(), ComplexMenu.class);
    }

    @Override
    public void run() {
        complexMenuStateCollector.collect(getFlowchart()).forEach(complexMenu -> {
            Menu menu = new Menu();

            complexMenu.childrenStream().map(ComplexOption.class::cast)
                    .forEach(complexOption -> prepareOption(menu, complexOption));

            RenPyFlowchartManipulatorUtil.replace(complexMenu, menu);
        });
    }

    private void prepareOption(Menu menu, ComplexOption complexOption) {
        Option option = complexOption.getCondition() == null
                ? new Option(complexOption.getValue())
                : new ConditionalOption(complexOption.getValue(), complexOption.getCondition());

        joinChildren(complexOption);

        option.setNext(complexOption.childAt(0));
        complexOption.childAt(0)
                .removeRoot(complexOption);
        menu.addBranch(option);
    }
}
