package com.dododo.ariadne.thread.contract;

import com.dododo.ariadne.thread.model.EndBlock;
import com.dododo.ariadne.thread.model.EntryBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.OptionBlock;
import com.dododo.ariadne.thread.model.ConditionalOptionBlock;
import com.dododo.ariadne.thread.model.ReplyBlock;
import com.dododo.ariadne.thread.model.TextBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

public interface ThreadFlowchartContract {

    void accept(EntryBlock block);

    void accept(TextBlock block);

    void accept(ReplyBlock block);

    void accept(MenuBlock block);

    void accept(OptionBlock block);

    void accept(ConditionalOptionBlock block);

    void accept(SwitchBlock block);

    void accept(EndBlock block);
}
