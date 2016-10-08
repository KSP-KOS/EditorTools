package ksp.kos.ideaplugin.reference;


import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalContext extends Context<KerboScriptNamedElement> {

    public LocalContext(LocalContext parent) {
        super(parent);
    }

    @Override
    public KerboScriptFile getKerboScriptFile() {
        return parent.getKerboScriptFile();
    }

    @Override
    @Nullable
    public KerboScriptNamedElement findLocalDeclaration(Reference reference) {
        KerboScriptNamedElement declaration = getDeclarations(reference.getReferableType()).get(reference.getName());
        if (declaration!=null && reference instanceof KerboScriptBase) {
            KerboScriptBase element = (KerboScriptBase) reference;
            if (declaration.getTextOffset() > element.getTextOffset() &&
                    element.getScope().getCachedScope() == this) {
                return null;
            }
        }
        return declaration;
    }
}
