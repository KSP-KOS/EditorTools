package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public interface Differ {
    boolean canDo(KerboScriptInstruction instruction);
    void doIt(Project project, KerboScriptInstruction instruction) throws ActionFailedException;
}
