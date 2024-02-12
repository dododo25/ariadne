package com.dododo.ariadne.mxg.common.contract;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;

public abstract class BlockSimpleFlowchartContract implements BlockFlowchartContract {

    @Override
    public final void accept(EntryBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(TextBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(ReplyBlock block) {
        acceptBlock(block);
    }

    @Override
    public void accept(MenuBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(OptionBlock block) {
        acceptBlock(block);
    }

    @Override
    public final void accept(ConditionalOptionBlock block) {
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
