package ksp.kos.ideaplugin.reference;

import com.intellij.openapi.project.Project;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;

import java.util.function.BiFunction;

/**
 * Created on 08/01/16.
 *
 * @author ptasha
 */
public class FileScope extends LocalScope {
    private KerboScriptFile kerboScriptFile;
    private final ScopeMap imports = new ScopeMap();
    private final ScopeMap virtualFunctions = new ScopeMap();
    private final ScopeMap virtualVariables = new ScopeMap();

    public FileScope(KerboScriptFile kerboScriptFile) {
        this.kerboScriptFile = kerboScriptFile;
    }

    public void addDependency(KerboScriptNamedElement element) {
        String name = KerboScriptFile.stripExtension(element.getName());
        imports.put(name, element);
    }

    public ScopeMap getImports() {
        return imports;
    }

    @Override
    public void clear() {
        imports.clear();
        virtualFunctions.clear();
        virtualVariables.clear();
        super.clear();
    }

    public KerboScriptNamedElement getVirtualFunction(KerboScriptNamedElement element) {
        KerboScriptNamedElement function = virtualFunctions.get(element.getName());
        if (function == null) {
            function = createVirtualFunction(element.getName());
            virtualFunctions.put(element.getName(), function);
        }
        return function;
    }

    @SuppressWarnings("ConstantConditions")
    private KerboScriptDeclareFunctionClause createVirtualFunction(String name) {
        return KerboScriptElementFactory.function(kerboScriptFile.getProject(), name);
    }

    public KerboScriptNamedElement getVirtualVariable(KerboScriptNamedElement element) {
        KerboScriptNamedElement variable = virtualVariables.get(element.getName());
        if (variable == null) {
            variable = createVirtualVariable(element.getName());
            virtualVariables.put(element.getName(), variable);
        }
        return variable;
    }

    @SuppressWarnings("ConstantConditions")
    private KerboScriptNamedElement createVirtualVariable(String name) {
        Project project = kerboScriptFile.getProject();
        return KerboScriptElementFactory.variable(project, name);
    }

    public static <T> KerboScriptNamedElement resolve(KerboScriptFile file, BiFunction<FileScope, T, KerboScriptNamedElement> getElement, T item) {
        KerboScriptNamedElement resolved = getElement.apply(file.getFileScope(), item);
        if (resolved == null) {
            for (KerboScriptNamedElement run : file.getFileScope().getImports().values()) {
                KerboScriptFile dependency = file.resolveFile(run);
                if (dependency != null) {
                    resolved = getElement.apply(dependency.getFileScope(), item);
                    if (resolved != null) {
                        return resolved;
                    }
                }
            }
            return null;
        }
        return resolved;
    }

    public enum Resolver {
        FUNCTION(FileScope::resolveFunction, FileScope::getVirtualFunction),
        VARIABLE(FileScope::resolveVariable, FileScope::getVirtualVariable);

        private final BiFunction<FileScope, KerboScriptNamedElement, KerboScriptNamedElement> findRegistered;
        private final BiFunction<FileScope, KerboScriptNamedElement, KerboScriptNamedElement> createVirtual;

        Resolver(BiFunction<FileScope, KerboScriptNamedElement, KerboScriptNamedElement> findRegistered,
                 BiFunction<FileScope, KerboScriptNamedElement, KerboScriptNamedElement> createVirtual) {
            this.findRegistered = findRegistered;
            this.createVirtual = createVirtual;
        }

        public KerboScriptNamedElement resolve(KerboScriptFile file,
                                  KerboScriptNamedElement element) {
            KerboScriptNamedElement resolved = FileScope.resolve(file, findRegistered, element);
            if (resolved == null) {
                return createVirtual.apply(file.getFileScope(), element);
            }
            return resolved;
        }
    }
}
