package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.ideaplugin.reference.ReferenceType;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends KerboScriptBase, Reference {
    ReferenceType getType();
    void setType(ReferenceType type);

    @Override
    default KerboScriptScope getKingdom() {
        return getScope();
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
}
