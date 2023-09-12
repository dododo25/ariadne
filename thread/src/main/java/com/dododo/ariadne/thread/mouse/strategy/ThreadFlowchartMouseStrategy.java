package com.dododo.ariadne.thread.mouse.strategy;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.ChainBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

import java.util.Set;

public interface ThreadFlowchartMouseStrategy {

    void acceptChainBlock(ChainBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited);

    void acceptMenuBlock(MenuBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited);

    void acceptSwitchBlock(SwitchBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited);
}
