package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.context.Context;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.utils.MapBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 08/01/16.
 *
 * @author ptasha
 */
public class PsiFileContext extends FileContext<KerboScriptNamedElement> {
    private final KerboScriptFile kerboScriptFile;

    public PsiFileContext(KerboScriptFile kerboScriptFile) {
        // TODO combine virtual context and resolver together into SuperContext for whole project
        super(new Context<>(null), kerboScriptFile.getPureName(), new PsiFileResolver(kerboScriptFile));
        this.kerboScriptFile = kerboScriptFile;
    }

    @Override
    protected KerboScriptNamedElement resolve(Reference reference, boolean createAllowed) {
        KerboScriptNamedElement resolved = super.resolve(reference, createAllowed);
        if (resolved == null && createAllowed) {
            return createVirtual(reference);
        }
        return resolved;
    }

    @Override
    protected void registerUnknown(ReferableType type, String name, KerboScriptNamedElement element) {
        if (type==ReferableType.FILE) {
            addDefinition(type, KerboScriptFile.stripExtension(name), element);
        } else {
            super.registerUnknown(type, name, element);
        }
    }

    @Override
    public void clear() {
        getParent().clear();
        super.clear();
    }

    private static final Map<ReferableType, String> MOCKS = new MapBuilder<ReferableType, String>(new HashMap<>())
            .put(ReferableType.VARIABLE, "local %s to 0.")
            .put(ReferableType.FUNCTION, "function %s {}").getMap();

    public KerboScriptNamedElement createVirtual(Reference reference) {
        String text = MOCKS.get(reference.getReferableType());
        if (text == null) {
            return null;
        }
        KerboScriptInstruction instruction = KerboScriptElementFactory.instruction(kerboScriptFile.getProject(), String.format(text, reference.getName()));
        KerboScriptNamedElement declaration = instruction.downTill(KerboScriptNamedElement.class);
        getParent().register(declaration);
        return declaration;
    }

    @Override
    public KerboScriptNamedElement getFile() {
        return kerboScriptFile;
    }
}
