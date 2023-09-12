package com.dododo.ariadne.thread.contract;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.EndBlock;
import com.dododo.ariadne.thread.model.EntryBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.OptionBlock;
import com.dododo.ariadne.thread.model.ConditionalOptionBlock;
import com.dododo.ariadne.thread.model.ReplyBlock;
import com.dododo.ariadne.thread.model.TextBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

public abstract class ThreadSimpleFlowchartContract implements ThreadFlowchartContract {

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
