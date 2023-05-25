package com.dododo.ariadne.drawio.contract;

import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

public interface DrawIoFlowchartContract {

    void accept(EntryBlock block);

    void accept(StatementBlock block);

    void accept(SwitchBlock block);

    void accept(EndBlock block);
}
