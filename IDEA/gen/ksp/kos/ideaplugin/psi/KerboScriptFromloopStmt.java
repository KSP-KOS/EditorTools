// This is a generated file. Not intended for manual editing.
package ksp.kos.ideaplugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface KerboScriptFromloopStmt extends PsiElement {

  @NotNull
  KerboScriptExpr getExpr();

  @NotNull
  KerboScriptInstruction getInstruction();

  @NotNull
  List<KerboScriptInstructionBlock> getInstructionBlockList();

}
