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

public class KerboScriptDeclareStmtImpl extends ASTWrapperPsiElement implements KerboScriptDeclareStmt {

  public KerboScriptDeclareStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof KerboScriptVisitor) ((KerboScriptVisitor)visitor).visitDeclareStmt(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public KerboScriptDeclareFunctionClause getDeclareFunctionClause() {
    return findChildByClass(KerboScriptDeclareFunctionClause.class);
  }

  @Override
  @Nullable
  public KerboScriptDeclareIdentifierClause getDeclareIdentifierClause() {
    return findChildByClass(KerboScriptDeclareIdentifierClause.class);
  }

  @Override
  @Nullable
  public KerboScriptDeclareLockClause getDeclareLockClause() {
    return findChildByClass(KerboScriptDeclareLockClause.class);
  }

  @Override
  @Nullable
  public KerboScriptDeclareParameterClause getDeclareParameterClause() {
    return findChildByClass(KerboScriptDeclareParameterClause.class);
  }

}
