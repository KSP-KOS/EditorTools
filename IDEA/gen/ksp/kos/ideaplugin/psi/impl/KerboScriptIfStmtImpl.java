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

public class KerboScriptIfStmtImpl extends ASTWrapperPsiElement implements KerboScriptIfStmt {

  public KerboScriptIfStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof KerboScriptVisitor) ((KerboScriptVisitor)visitor).visitIfStmt(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public KerboScriptExpr getExpr() {
    return findNotNullChildByClass(KerboScriptExpr.class);
  }

  @Override
  @NotNull
  public List<KerboScriptInstruction> getInstructionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, KerboScriptInstruction.class);
  }

}
