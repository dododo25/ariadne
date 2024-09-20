package com.dododo.ariadne.drawio.contract;

import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.model.TextBlock;

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
