package ksp.kos.ideaplugin.psi;

import com.intellij.openapi.util.Key;
import com.intellij.psi.util.CachedValue;
import ksp.kos.ideaplugin.dataflow.FlowParser;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.reference.PsiSelfResolvable;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.ideaplugin.reference.ReferenceType;
import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends KerboScriptBase, PsiSelfResolvable {

    Key<CachedValue<ReferenceFlow>> FLOW_KEY = new Key<>("ksp.kos.ideaplugin.semantics");

    ReferenceType getType();
    void setType(ReferenceType type);

    default ReferenceFlow getCachedFlow() { // TODO duality can be pure virtual
        CachedValue<ReferenceFlow> cached = getUserData(FLOW_KEY);
        if (cached == null) {
            cached = createCachedValue(() -> FlowParser.INSTANCE.apply(this));
            putUserData(FLOW_KEY, cached);
        }
        return cached.getValue();
    }

    @Override
    default LocalContext getKingdom() {
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
        return PsiSelfResolvable.super.resolve();
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
