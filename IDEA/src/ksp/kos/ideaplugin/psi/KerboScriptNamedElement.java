package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.context.Context;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.ideaplugin.reference.ReferenceType;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends KerboScriptBase, Reference<KerboScriptNamedElement> {
    ReferenceType getType();
    void setType(ReferenceType type);

    @Override
    default Context<KerboScriptNamedElement> getKingdom() {
        return getScope().getCachedScope();
    }

    @Override
    default ReferableType getReferableType() {
        return getType().getType();
    }

    default boolean isDeclaration() {
        return getType().getOccurrenceType().isDeclaration();
    }

    @Override
    default KerboScriptNamedElement resolve() {
        if (isDeclaration()) {
            return this;
        }
        return Reference.super.resolve();
    }

    @Override
    default boolean matches(Reference declaration) {
        if (declaration instanceof KerboScriptNamedElement) {
            KerboScriptNamedElement element = (KerboScriptNamedElement) declaration;
            if (element.getTextOffset() > this.getTextOffset() &&
                    declaration.getKingdom() == this.getKingdom()) {
                return false;
            }
        }
        return true;
    }
}
