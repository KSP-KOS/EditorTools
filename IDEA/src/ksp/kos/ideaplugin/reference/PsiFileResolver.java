package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.ideaplugin.reference.context.FileContextResolver;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public class PsiFileResolver implements FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> {
    private final KerboScriptFile anyFile;

    public PsiFileResolver(KerboScriptFile anyFile) {
        this.anyFile = anyFile;
    }

    @Override
    public FileContext<KerboScriptNamedElement> resolveFile(String name) {
        KerboScriptFile file = anyFile.resolveFile(name);
        if (file!=null) {
            return file.getCachedScope();
        }
        return null;
    }
}
