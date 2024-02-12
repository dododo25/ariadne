package com.dododo.ariadne.mxg.common.contract;

import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;

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
