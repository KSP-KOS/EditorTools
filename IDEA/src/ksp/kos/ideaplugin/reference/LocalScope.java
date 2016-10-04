package ksp.kos.ideaplugin.reference;


import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalScope extends AbstractScope<KerboScriptNamedElement> {

    public LocalScope(LocalScope parent) {
        super(parent);
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
