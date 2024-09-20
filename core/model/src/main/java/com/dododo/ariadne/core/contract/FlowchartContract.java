package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;

public interface FlowchartContract {

    void accept(EntryState state);

    void accept(Text text);

    void accept(Reply reply);

    void accept(Menu menu);

    void accept(Option option);

    void accept(ConditionalOption option);

    void accept(Switch aSwitch);

    void accept(EndPoint point);
}
