// This is a generated file. Not intended for manual editing.
package ksp.kos.ideaplugin.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static ksp.kos.ideaplugin.psi.KerboScriptTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import ksp.kos.ideaplugin.psi.*;

public class KerboScriptFromloopStmtImpl extends ASTWrapperPsiElement implements KerboScriptFromloopStmt {

  public KerboScriptFromloopStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof KerboScriptVisitor) ((KerboScriptVisitor)visitor).visitFromloopStmt(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public KerboScriptExpr getExpr() {
    return findNotNullChildByClass(KerboScriptExpr.class);
  }

  @Override
  @NotNull
  public KerboScriptInstruction getInstruction() {
    return findNotNullChildByClass(KerboScriptInstruction.class);
  }

  @Override
  @NotNull
  public List<KerboScriptInstructionBlock> getInstructionBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, KerboScriptInstructionBlock.class);
  }

}
