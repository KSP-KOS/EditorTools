package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.dataflow.Flow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public abstract class DuplicateDiffer<P extends KerboScriptBase> implements Differ {
    private final Class<P> clazz;

    public DuplicateDiffer(Class<P> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        return clazz.isInstance(instruction);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doIt(Project project, KerboScriptInstruction instruction) throws ActionFailedException {
        P variable = (P) instruction;
        KerboScriptInstruction copy = (KerboScriptInstruction) instruction.copy();
        separator(copy);
        instruction.getParent().addBefore(copy, instruction);
        PsiElement diff = variable.replace(KerboScriptElementFactory.instruction(project, diff(variable)));
        CodeStyleManager.getInstance(project).reformatNewlyAddedElement(diff.getParent().getNode(), diff.getNode());
    }

    protected void separator(KerboScriptBase copy) {
        copy.newLine();
    }

    protected String diff(P variable) throws ActionFailedException {
        try {
            return parse(variable).differentiate(variable.getScope().getCachedScope()).getText();
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }

    protected abstract Flow parse(P variable) throws SyntaxException;
}
