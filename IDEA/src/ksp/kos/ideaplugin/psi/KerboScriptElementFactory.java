package ksp.kos.ideaplugin.psi;

import com.intellij.lang.ASTFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.KerboScriptLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 22/01/16.
 *
 * @author ptasha
 */
public class KerboScriptElementFactory {

    public static final String FILE_NAME = "generated.ks";

    public static KerboScriptFile file(String text) {
        return file(ProjectManager.getInstance().getDefaultProject(), text);
    }

    public static KerboScriptFile file(Project project, String text) {
        return (KerboScriptFile) PsiFileFactory.getInstance(project).createFileFromText(
                    FILE_NAME, KerboScriptLanguage.INSTANCE,
                    text, false, false);
    }

    public static KerboScriptExpr expression(Project project, String expr) {
        KerboScriptInstruction instruction = instruction(project, "return " + expr+".");
        return ((KerboScriptReturnStmt)instruction).getExpr();
    }

    public static KerboScriptInstruction instruction(Project project, String text) {
        KerboScriptFile file = file(project, text);
        PsiElement child = file.getFirstChild();
        if (child instanceof KerboScriptInstruction) {
            return ((KerboScriptInstruction) child);
        } else {
            throw new IllegalArgumentException(text+" is not instruction");
        }
    }

    @NotNull
    public static LeafElement leaf(IElementType type, String text) {
        LeafElement leaf = ASTFactory.leaf(type, text);
        CodeEditUtil.setNodeGenerated(leaf, true);
        return leaf;
    }
}
