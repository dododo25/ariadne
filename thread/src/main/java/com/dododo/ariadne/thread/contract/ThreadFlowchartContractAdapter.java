package com.dododo.ariadne.thread.contract;

import com.dododo.ariadne.thread.model.EndBlock;
import com.dododo.ariadne.thread.model.EntryBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.OptionBlock;
import com.dododo.ariadne.thread.model.ConditionalOptionBlock;
import com.dododo.ariadne.thread.model.ReplyBlock;
import com.dododo.ariadne.thread.model.TextBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

public abstract class ThreadFlowchartContractAdapter implements ThreadFlowchartContract {

    @Override
    public void accept(EntryBlock block) {}

    @Override
    public void accept(TextBlock block) {}

    @Override
    public void accept(ReplyBlock block) {}

    @Override
    public void accept(MenuBlock block) {}

    @Override
    public void accept(OptionBlock block) {}

    @Override
    public void accept(ConditionalOptionBlock block) {}

    @Override
    public void accept(SwitchBlock block) {}

    @Override
    public void accept(EndBlock block) {}
}
