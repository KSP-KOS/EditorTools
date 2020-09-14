package ksp.kos.ideaplugin.reference;

import com.intellij.openapi.diagnostic.Logger;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import ksp.kos.ideaplugin.reference.context.PsiDuality;
import ksp.kos.utils.MapBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 08/01/16.
 *
 * @author ptasha
 */
public class PsiFileContext extends FileContext {
    private static final Logger log = Logger.getInstance(PsiFileContext.class);

    private final KerboScriptFile kerboScriptFile;

    public PsiFileContext(KerboScriptFile kerboScriptFile) {
        // TODO combine virtual context and resolver together into SuperContext for whole project
        super(new LocalContext(null), kerboScriptFile.getPureName(), new PsiFileResolver(kerboScriptFile));
        this.kerboScriptFile = kerboScriptFile;
    }

    @Override
    protected Duality resolve(Reference reference, boolean createAllowed) {
        Duality resolved = super.resolve(reference, createAllowed);
        if (resolved == null && createAllowed) {
            return createVirtual(reference);
        }
        return resolved;
    }

    @Override
    public void clear() {
        getParent().clear();
        super.clear();
    }

    private static final Map<ReferableType, String> MOCKS = new MapBuilder<ReferableType, String>(new HashMap<>())
            .put(ReferableType.VARIABLE, "local %s to 0.")
            .put(ReferableType.FUNCTION, "function %s {}").getMap();

    public PsiDuality createVirtual(Reference reference) {
        String text = MOCKS.get(reference.getReferableType());
        if (text == null) {
            return null;
        }
        try {
            KerboScriptInstruction instruction = KerboScriptElementFactory.instruction(kerboScriptFile.getProject(), String.format(text, reference.getName()));
            PsiDuality declaration = new PsiDuality(instruction.downTill(KerboScriptNamedElement.class));
            getParent().register(declaration);
            return declaration;
        } catch (IllegalArgumentException e) {
            log.error("Failed to create virtual reference", e);
            return null;
        }
    }

    @Override
    public KerboScriptFile getSyntax() {
        return kerboScriptFile;
    }
}
