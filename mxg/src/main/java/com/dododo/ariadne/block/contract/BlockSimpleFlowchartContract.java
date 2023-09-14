package com.dododo.ariadne.block.contract;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;

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
