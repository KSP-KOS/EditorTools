package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.actions.BaseAction;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

/**
 * Created on 19/01/16.
 *
 * @author ptasha
 */
public class Differentiate extends BaseAction {
    private final static Differ[] differs = new Differ[]{
            new DeclareDiffer(),
            new SetDiffer(),
            new ReturnDiffer(),
            new FunctionDiffer()
    };

    @Override
    public void actionPerformed(AnActionEvent event) {
        processPair(event, (instruction, differ) -> {
            Project project = getEventProject(event);
            WriteCommandAction.runWriteCommandAction(project, () -> {
                try {
                    differ.doIt(project, instruction);
                } catch (ActionFailedException e) {
                    Messages.showDialog(e.getMessage(), "Failed:", new String[]{"OK"}, -1, null);
                }
            });
            return null;
        });
    }

    public <R> R processPair(AnActionEvent event, BiFunction<KerboScriptInstruction, Differ, R> pair) {
        KerboScriptInstruction instruction = getInstruction(event);
        if (instruction != null) {
            Differ differ = findDiffer(instruction);
            if (differ != null) {
                return pair.apply(instruction, differ);
            }
        }
        return null;
    }

    @Nullable
    public KerboScriptInstruction getInstruction(AnActionEvent event) {
        return PsiTreeUtil.getParentOfType(getPsiElement(event), KerboScriptInstruction.class, false);
    }

    private Differ findDiffer(KerboScriptInstruction instruction) {
        for (Differ differ : differs) {
            if (differ.canDo(instruction)) {
                return differ;
            }
        }
        return null;
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(processPair(event, (instruction, differ) -> true) != null);
    }
}
