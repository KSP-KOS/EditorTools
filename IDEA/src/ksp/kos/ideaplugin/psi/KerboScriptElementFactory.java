package ksp.kos.ideaplugin.psi;

import com.intellij.lang.ASTFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
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

    public static KerboScriptDeclareFunctionClause function(Project project, String name) {
        KerboScriptFile file = file(project, "function " + name + " {}");
        KerboScriptInstruction instruction = (KerboScriptInstruction) file.getFirstChild();
        return ((KerboScriptDeclareStmt) instruction).getDeclareFunctionClause();

    }

    public static KerboScriptFile file(String text) {
        return file(ProjectManager.getInstance().getDefaultProject(), text);
    }

    public static KerboScriptFile file(Project project, String text) {
        return (KerboScriptFile) PsiFileFactory.getInstance(project).createFileFromText(
                    FILE_NAME, KerboScriptLanguage.INSTANCE,
                    text, false, false);
    }

    public static KerboScriptNamedElement variable(Project project, String name) {
        KerboScriptFile file = file(project, "local " + name + " to 0.");
        KerboScriptInstruction instruction = (KerboScriptInstruction) file.getFirstChild();
        return ((KerboScriptDeclareStmt) instruction).getDeclareIdentifierClause();
    }

    public static KerboScriptExpr expression(Project project, String expr) {
        KerboScriptFile file = file(project, "return " + expr+".");
        return ((KerboScriptReturnStmt)file.getFirstChild()).getExpr();
    }

    public static KerboScriptInstruction instruction(Project project, String text) {
        KerboScriptFile file = file(project, text);
        PsiElement child = file.getFirstChild();
        if (child instanceof KerboScriptInstruction) {
            return ((KerboScriptInstruction) child);
        } else {
            throw new RuntimeException(text+" is not instruction");
        }
    }

    @NotNull
    public static LeafElement leaf(IElementType type, String text) {
        LeafElement leaf = ASTFactory.leaf(type, text);
        CodeEditUtil.setNodeGenerated(leaf, true);
        return leaf;
    }
}
