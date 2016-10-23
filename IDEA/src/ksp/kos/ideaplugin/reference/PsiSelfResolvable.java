package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public interface PsiSelfResolvable extends Reference {

    static PsiSelfResolvable variable(KerboScriptScope kingdom, String name) {
        return reference(kingdom.getCachedScope(), ReferableType.VARIABLE, name);
    }

    static PsiSelfResolvable function(KerboScriptScope kingdom, String name) {
        return function(kingdom.getCachedScope(), name);
    }

    static PsiSelfResolvable function(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    @NotNull
    static PsiSelfResolvable reference(LocalContext kingdom, ReferableType type, String name) {
        return new PsiReferenceImpl(kingdom, type, name);
    }

    static PsiSelfResolvable copy(Reference reference) {
        return reference(reference.getKingdom(), reference.getReferableType(), reference.getName());
    }

    default KerboScriptNamedElement resolve() {
        return Duality.getSyntax(getKingdom().resolve(this));
    }

    default KerboScriptNamedElement findDeclaration() {
        return Duality.getSyntax(getKingdom().findDeclaration(this));
    }
}
