package ksp.kos.ideaplugin.format;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptIfStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptInstructionBlock;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created on 17/01/16.
 *
 * @author ptasha
 */
public class KerboScriptBlock extends AbstractBlock {
    private static final Set<IElementType> IF_ELSE = new HashSet<>(Arrays.asList(KerboScriptTypes.IF, KerboScriptTypes.ELSE));

    protected KerboScriptBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();
        while (child != null) {
            addChild(blocks, child);
            child = child.getTreeNext();
        }
        return blocks;
    }

    private void addChild(List<Block> blocks, ASTNode child) {
        if (child.getElementType() != TokenType.WHITE_SPACE && child.getTextRange().getLength()>0) {
            Block block = new KerboScriptBlock(child, null, null);
            blocks.add(block);
        }
    }

    @Override
    public Indent getIndent() {
        PsiElement psi = getNode().getPsi();
        if (psi.getParent() == null || psi.getParent() instanceof KerboScriptFile) {
            return Indent.getNoneIndent();
        } else if (psi.getParent() instanceof KerboScriptInstructionBlock) {
            if (psi instanceof KerboScriptInstruction) {
                return Indent.getNormalIndent();
            } else {
                return Indent.getNoneIndent();
            }
        } else if (psi.getParent() instanceof KerboScriptIfStmt && IF_ELSE.contains(psi.getNode().getElementType())) {
            return Indent.getNoneIndent();
        } else {
//            return Indent.getNormalIndent();
            return Indent.getContinuationWithoutFirstIndent();
        }
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return getNode() instanceof LeafElement;
    }

    @Nullable
    @Override
    protected Indent getChildIndent() {
        return getNode().getPsi() instanceof KerboScriptInstructionBlock?Indent.getNormalIndent():Indent.getNoneIndent();
    }
}
