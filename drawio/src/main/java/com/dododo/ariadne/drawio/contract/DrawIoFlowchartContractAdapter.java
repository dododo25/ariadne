package com.dododo.ariadne.drawio.contract;

import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

public abstract class DrawIoFlowchartContractAdapter implements DrawIoFlowchartContract {

    @Override
    public void accept(EntryBlock block) {}

    @Override
    public void accept(StatementBlock block) {}

    @Override
    public void accept(SwitchBlock block) {}

    @Override
    public void accept(EndBlock block) {}
}
