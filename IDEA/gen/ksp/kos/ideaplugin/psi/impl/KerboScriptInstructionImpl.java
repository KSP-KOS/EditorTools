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

public class KerboScriptInstructionImpl extends ASTWrapperPsiElement implements KerboScriptInstruction {

  public KerboScriptInstructionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof KerboScriptVisitor) ((KerboScriptVisitor)visitor).visitInstruction(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public KerboScriptAddStmt getAddStmt() {
    return findChildByClass(KerboScriptAddStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptBreakStmt getBreakStmt() {
    return findChildByClass(KerboScriptBreakStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptClearStmt getClearStmt() {
    return findChildByClass(KerboScriptClearStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptCompileStmt getCompileStmt() {
    return findChildByClass(KerboScriptCompileStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptCopyStmt getCopyStmt() {
    return findChildByClass(KerboScriptCopyStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptDeclareStmt getDeclareStmt() {
    return findChildByClass(KerboScriptDeclareStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptDeleteStmt getDeleteStmt() {
    return findChildByClass(KerboScriptDeleteStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptDirective getDirective() {
    return findChildByClass(KerboScriptDirective.class);
  }

  @Override
  @Nullable
  public KerboScriptEditStmt getEditStmt() {
    return findChildByClass(KerboScriptEditStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptForStmt getForStmt() {
    return findChildByClass(KerboScriptForStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptFromloopStmt getFromloopStmt() {
    return findChildByClass(KerboScriptFromloopStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptIdentifierLedStmt getIdentifierLedStmt() {
    return findChildByClass(KerboScriptIdentifierLedStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptIfStmt getIfStmt() {
    return findChildByClass(KerboScriptIfStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptInstructionBlock getInstructionBlock() {
    return findChildByClass(KerboScriptInstructionBlock.class);
  }

  @Override
  @Nullable
  public KerboScriptListStmt getListStmt() {
    return findChildByClass(KerboScriptListStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptLogStmt getLogStmt() {
    return findChildByClass(KerboScriptLogStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptOnStmt getOnStmt() {
    return findChildByClass(KerboScriptOnStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptPreserveStmt getPreserveStmt() {
    return findChildByClass(KerboScriptPreserveStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptPrintStmt getPrintStmt() {
    return findChildByClass(KerboScriptPrintStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptRebootStmt getRebootStmt() {
    return findChildByClass(KerboScriptRebootStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptRemoveStmt getRemoveStmt() {
    return findChildByClass(KerboScriptRemoveStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptRenameStmt getRenameStmt() {
    return findChildByClass(KerboScriptRenameStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptReturnStmt getReturnStmt() {
    return findChildByClass(KerboScriptReturnStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptRunStmt getRunStmt() {
    return findChildByClass(KerboScriptRunStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptSetStmt getSetStmt() {
    return findChildByClass(KerboScriptSetStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptShutdownStmt getShutdownStmt() {
    return findChildByClass(KerboScriptShutdownStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptStageStmt getStageStmt() {
    return findChildByClass(KerboScriptStageStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptSwitchStmt getSwitchStmt() {
    return findChildByClass(KerboScriptSwitchStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptToggleStmt getToggleStmt() {
    return findChildByClass(KerboScriptToggleStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptUnlockStmt getUnlockStmt() {
    return findChildByClass(KerboScriptUnlockStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptUnsetStmt getUnsetStmt() {
    return findChildByClass(KerboScriptUnsetStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptUntilStmt getUntilStmt() {
    return findChildByClass(KerboScriptUntilStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptWaitStmt getWaitStmt() {
    return findChildByClass(KerboScriptWaitStmt.class);
  }

  @Override
  @Nullable
  public KerboScriptWhenStmt getWhenStmt() {
    return findChildByClass(KerboScriptWhenStmt.class);
  }

}
