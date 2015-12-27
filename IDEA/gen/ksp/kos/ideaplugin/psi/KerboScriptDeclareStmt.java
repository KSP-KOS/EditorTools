// This is a generated file. Not intended for manual editing.
package ksp.kos.ideaplugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface KerboScriptDeclareStmt extends PsiElement {

  @Nullable
  KerboScriptDeclareFunctionClause getDeclareFunctionClause();

  @Nullable
  KerboScriptDeclareIdentifierClause getDeclareIdentifierClause();

  @Nullable
  KerboScriptDeclareLockClause getDeclareLockClause();

  @Nullable
  KerboScriptDeclareParameterClause getDeclareParameterClause();

}
