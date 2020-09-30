package ksp.kos.ideaplugin.reference;

import com.intellij.psi.PsiFileFactory;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.KerboScriptLanguage;
import ksp.kos.ideaplugin.reference.context.FileContextResolver;
import ksp.kos.ideaplugin.reference.context.FileDuality;
import ksp.kos.ideaplugin.reference.context.PsiFileDuality;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public class PsiFileResolver implements FileContextResolver {
    private final KerboScriptFile anyFile;

    public PsiFileResolver(KerboScriptFile anyFile) {
        this.anyFile = anyFile;
    }

    @Override
    public @NotNull FileDuality ensureFile(String name) {
        KerboScriptFile file = anyFile.findFile(name);
        if (file == null) {
            file = (KerboScriptFile) PsiFileFactory.getInstance(anyFile.getProject()).createFileFromText(
                    name + ".ks", KerboScriptLanguage.INSTANCE, "@lazyglobal off.");
            file = (KerboScriptFile) anyFile.getContainingDirectory().add(file);
        }
        return file;
    }

    @Override
    public FileDuality resolveFile(String name) {
        KerboScriptFile file = anyFile.resolveFile(name);
        if (file!=null) {
            return new PsiFileDuality(file);
        }
        return null;
    }
}
