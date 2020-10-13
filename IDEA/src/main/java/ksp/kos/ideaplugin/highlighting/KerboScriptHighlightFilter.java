package ksp.kos.ideaplugin.highlighting;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import ksp.kos.ideaplugin.KerboScriptFileType;

/**
 * Created on 09/01/16.
 *
 * @author ptasha
 */
public class KerboScriptHighlightFilter implements Condition<VirtualFile> {
    private final Project project;

    public KerboScriptHighlightFilter(Project project) {
        this.project = project;
    }

    @Override
    public boolean value(final VirtualFile file) {
        return file.getFileType() == KerboScriptFileType.INSTANCE
                && !CompilerManager.getInstance(project).isExcludedFromCompilation(file);
    }
}
