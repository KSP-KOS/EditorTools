package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.utils.MapBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 08/01/16.
 *
 * @author ptasha
 */
public class FileScope extends LocalScope {
    private KerboScriptFile kerboScriptFile;

    public FileScope(KerboScriptFile kerboScriptFile) {
        this(kerboScriptFile, new LocalScope(null));
    }

    private FileScope(KerboScriptFile kerboScriptFile, LocalScope virtual) {
        super(virtual);
        this.kerboScriptFile = kerboScriptFile;
    }

    @Override
    public KerboScriptNamedElement findDeclaration(Reference reference) {
        if (reference.getReferableType()==ReferableType.FILE) {
            return resolveFile(reference.getName());
        }
        KerboScriptNamedElement resolved = super.findDeclaration(reference);
        if (resolved == null) {
            for (KerboScriptNamedElement run : getImports().values()) {
                KerboScriptFile dependency = resolveFile(run.getName());
                if (dependency != null) {
                    resolved = dependency.getCachedScope().findLocalDeclaration(reference);
                    if (resolved != null) {
                        return resolved;
                    }
                }
            }
            return null;
        }
        return resolved;
    }

    @Override
    public KerboScriptNamedElement resolve(Reference reference) {
        KerboScriptNamedElement resolved = super.resolve(reference);
        if (resolved == null) {
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

    public ScopeMap getImports() {
        return getDeclarations(ReferableType.FILE);
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

    private KerboScriptFile resolveFile(String name) {
        return kerboScriptFile.resolveFile(name);
    }

}
