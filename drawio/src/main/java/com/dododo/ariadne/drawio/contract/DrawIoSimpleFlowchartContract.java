package com.dododo.ariadne.drawio.contract;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

public abstract class DrawIoSimpleFlowchartContract implements DrawIoFlowchartContract {

    @Override
    public final void accept(EntryBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(StatementBlock block) {
        acceptBlock(block);
    }

    @Override
    public void accept(SwitchBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(EndBlock block) {
        acceptBlock(block);
    }

    public abstract void acceptBlock(Block block);
}
