package com.dododo.ariadne.drawio.contract;

import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

public abstract class DrawIoFlowchartContractAdapter implements DrawIoFlowchartContract {

    @Override
    public void accept(EntryBlock block) {}

    @Override
    public void accept(StatementBlock block) {}

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
