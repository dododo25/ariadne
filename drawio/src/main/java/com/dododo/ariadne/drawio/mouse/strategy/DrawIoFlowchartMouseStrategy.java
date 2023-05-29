package com.dododo.ariadne.drawio.mouse.strategy;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

import java.util.Set;

public interface DrawIoFlowchartMouseStrategy {

    void acceptChainBlock(ChainBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited);

    void acceptMenuBlock(MenuBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited);

    void acceptSwitchBlock(SwitchBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited);
}
