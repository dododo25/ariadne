package com.dododo.ariadne.block.contract;

import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;

public interface BlockFlowchartContract {

    void accept(EntryBlock block);

    void accept(TextBlock block);

    void accept(ReplyBlock block);

    void accept(MenuBlock block);

    void accept(OptionBlock block);

    void accept(ConditionalOptionBlock block);

    void accept(SwitchBlock block);

    void accept(EndBlock block);
}
