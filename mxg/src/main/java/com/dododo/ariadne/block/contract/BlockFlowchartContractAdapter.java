package com.dododo.ariadne.block.contract;

import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;

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
