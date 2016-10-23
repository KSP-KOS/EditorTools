package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.reference.context.FileContextResolver;
import ksp.kos.ideaplugin.reference.context.FileDuality;
import ksp.kos.ideaplugin.reference.context.PsiFileDuality;

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
    public FileDuality resolveFile(String name) {
        KerboScriptFile file = anyFile.resolveFile(name);
        if (file!=null) {
            return new PsiFileDuality(file);
        }
        return null;
    }
}
