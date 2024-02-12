package com.dododo.ariadne.mxg.common.contract;

import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;

public abstract class BlockFlowchartContractAdapter implements BlockFlowchartContract {

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
