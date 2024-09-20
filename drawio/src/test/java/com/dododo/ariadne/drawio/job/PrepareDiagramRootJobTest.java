package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.model.TextBlock;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxAbstractCell;
import com.dododo.ariadne.mxg.model.MxEdgeCell;
import com.dododo.ariadne.mxg.model.MxFile;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.geometry.EdgeGeometry;
import com.dododo.ariadne.mxg.geometry.RelativeNodeGeometry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrepareDiagramRootJobTest {

    private MxFile mxFile;

    private DiagramRoot root;

    @BeforeEach
    void setUp() {
        root = new DiagramRoot();
        mxFile = createMxFile(root);
    }

    @Test
    void testRunWhenRootBlockIsEntryStateShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new EntryBlock(id);
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsTextShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new TextBlock(id, "test");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("test")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsReplyWithoutCharacterShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new ReplyBlock(id, null, "test");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("<i>test</i>")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsReplyWithCharacterShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new ReplyBlock(id, "test1", "test2");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("<p>test1</p><p><i>test2</i></p>")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsOptionShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new OptionBlock(id, "test");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("<i>test</i>")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsOptionalOptionShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new ConditionalOptionBlock(id, "test1", "test2");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("<p><i>test1</i></p><p>if test2</p>")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsMenuShouldDoneWell() {
        int width = 100;

        MxNodeCell nodeCell1 = new MxNodeCell.Builder()
                .setId(0)
                .setGeometry(ComplexNodeGeometry.create(0, 0, width, 4))
                .build();

        MxNodeCell nodeCell2 = new MxNodeCell.Builder()
                .setId(0)
                .setIdSuffix(".1")
                .setParent(0)
                .setGeometry(ComplexNodeGeometry.create(0, 0, width, 4))
                .build();

        MxNodeCell nodeCell3 = new MxNodeCell.Builder()
                .setId(1)
                .setValue("<i>test</i>")
                .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                .build();

        MxNodeCell nodeCell4 = new MxNodeCell.Builder()
                .setId(2)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxEdgeCell edgeCell1 = new MxEdgeCell.NoVerticesBuilder()
                .setId(0)
                .setIdSuffix(".2")
                .setParent(0)
                .setGeometry(EdgeGeometry.create(0, 2, width, 2))
                .build();

        MxEdgeCell edgeCell2 = new MxEdgeCell.Builder()
                .setSource(0)
                .setTarget(1)
                .setEntryPoint(0.5, 0)
                .setExitPoint(0.5, 1)
                .build();

        MxEdgeCell edgeCell3 = new MxEdgeCell.Builder()
                .setSource(1)
                .setTarget(2)
                .setEntryPoint(0.5, 0)
                .setExitPoint(0.5, 1)
                .build();

        MenuBlock menuBlock = new MenuBlock(0);
        OptionBlock optionBlock = new OptionBlock(1, "test");
        EndBlock endBlock = new EndBlock(2);

        menuBlock.setWidth(width);
        menuBlock.addBranch(optionBlock);
        optionBlock.setNext(endBlock);

        testRunShouldDoneWell(menuBlock, nodeCell1, nodeCell2, edgeCell1, edgeCell2, nodeCell3, edgeCell3, nodeCell4);
    }

    @Test
    void testRunWhenRootBlockIsSwitchShouldDoneWell() {
        MxNodeCell nodeCell1 = new MxNodeCell.Builder()
                .setId(0)
                .setValue("test")
                .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 120))
                .build();

        MxNodeCell nodeCell2 = new MxNodeCell.Builder()
                .setId(1)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxNodeCell nodeCell3 = new MxNodeCell.Builder()
                .setId(2)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxEdgeCell edgeCell1 = new MxEdgeCell.Builder()
                .setSource(0)
                .setTarget(1)
                .setIdSuffix("t")
                .setExitPoint(0.5, 0)
                .setGeometry(EdgeGeometry.create(0, 0, 0, 120))
                .build();

        MxEdgeCell edgeCell2 = new MxEdgeCell.Builder()
                .setSource(0)
                .setTarget(2)
                .setIdSuffix("f")
                .setExitPoint(0.5, 0)
                .setGeometry(EdgeGeometry.create(0, 0, 160, 120))
                .build();

        MxNodeCell nodeCell4 = new MxNodeCell.ParentCellBuilder()
                .setId(edgeCell1, "tl")
                .setValue("true")
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        MxNodeCell nodeCell5 = new MxNodeCell.ParentCellBuilder()
                .setId(edgeCell2, "fl")
                .setValue("false")
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        SwitchBlock switchBlock = new SwitchBlock(0, "test");
        EndBlock endBlock1 = new EndBlock(1);
        EndBlock endBlock2 = new EndBlock(2);

        switchBlock.setTrueBranch(endBlock1);
        switchBlock.setFalseBranch(endBlock2);

        testRunShouldDoneWell(switchBlock, nodeCell1, edgeCell1, edgeCell2, nodeCell4, nodeCell5, nodeCell2, nodeCell3);
    }

    private void testRunWhenRootBlockIsChainBlock(BlockToMxCellFactory factory) {
        int rootBlockId = 0;
        int childBlockId = 1;

        MxNodeCell nodeCell1 = factory.createMxCell(rootBlockId);

        MxNodeCell nodeCell2 = new MxNodeCell.Builder()
                .setId(childBlockId)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxEdgeCell edgeCell = new MxEdgeCell.Builder()
                .setSource(rootBlockId)
                .setTarget(childBlockId)
                .setEntryPoint(0.5, 0)
                .setExitPoint(0.5, 1)
                .build();

        ChainBlock rootBlock = factory.createBlock(rootBlockId);
        EndBlock endBlock = new EndBlock(childBlockId);

        rootBlock.setNext(endBlock);

        testRunShouldDoneWell(rootBlock, nodeCell1, edgeCell, nodeCell2);
    }

    private void testRunShouldDoneWell(Block rootBlock, MxAbstractCell... expected) {
        PrepareDiagramRootJob job = new PrepareDiagramRootJob(new AtomicReference<>(mxFile),
                new AtomicReference<>(rootBlock));

        job.run();

        Assertions.assertIterableEquals(Arrays.asList(expected), root.getCells());
    }

    private MxFile createMxFile(DiagramRoot root) {
        MxFile file = mock(MxFile.class, RETURNS_DEEP_STUBS);

        when(file.getDiagram().getModel().getRoot())
                .thenReturn(root);

        return file;
    }

    private interface BlockToMxCellFactory {

        ChainBlock createBlock(int id);

        MxNodeCell createMxCell(int id);

    }
}
