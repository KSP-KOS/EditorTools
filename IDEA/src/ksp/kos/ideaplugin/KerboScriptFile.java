package ksp.kos.ideaplugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.CachedValue;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.HashMap;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.FileBasedIndex;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.psi.impl.KerboScriptElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFile extends PsiFileBase implements KerboScriptElement {
    private final CachedValue<FileScope> scope = createCachedValue(this::calcLocalDeclarations);
    private CachedValue<HashMap<String, KerboScriptDeclareFunctionClause>> virtualFunctions = createCachedValue(HashMap<String, KerboScriptDeclareFunctionClause>::new);
    private CachedValue<HashMap<String, KerboScriptNamedElement>> virtualVariables = createCachedValue(HashMap<String, KerboScriptNamedElement>::new);
    private CachedValue<Boolean> globalOff = createCachedValue(this::calcGlobalOff);

    protected KerboScriptFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, KerboScriptLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return KerboScriptFileType.INSTANCE;
    }

    @NotNull
    @Override
    public String getName() {
        String name = super.getName();
        if (name.endsWith(".ks")) {
            name = name.substring(0, name.length() - 3);
        }
        return name;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return super.setName(name + ".ks");
    }

    public PsiElement resolveFunction(KerboScriptElementImpl element) {
        return Resolver.FUNCTION.resolve(this, element);
    }

    public PsiElement resolveVariable(KerboScriptElementImpl element) {
        return Resolver.VARIABLE.resolve(this, element);
    }

    @Override
    public PsiReference findReferenceAt(int offset) {
        return super.findReferenceAt(offset);
    }

    public boolean isGlobalOff() {
        return globalOff.getValue();
    }

    private KerboScriptDeclareFunctionClause getVirtualFunction(KerboScriptElementImpl element) {
        KerboScriptDeclareFunctionClause function = virtualFunctions.getValue().get(element.getName());
        if (function == null) {
            function = createVirtualFunction(element.getName());
            virtualFunctions.getValue().put(element.getName(), function);
        }
        return function;
    }

    @SuppressWarnings("ConstantConditions")
    private KerboScriptDeclareFunctionClause createVirtualFunction(String name) {
        KerboScriptFile file = (KerboScriptFile) PsiFileFactory.getInstance(getProject()).createFileFromText("undefined.ks", KerboScriptLanguage.INSTANCE,
                "function " + name + " {}", false, false);
        KerboScriptInstruction instruction = (KerboScriptInstruction) file.getFirstChild();
        return instruction.getDeclareStmt().getDeclareFunctionClause();
    }

    private KerboScriptNamedElement getVirtualVariable(KerboScriptElementImpl element) {
        KerboScriptNamedElement variable = virtualVariables.getValue().get(element.getName());
        if (variable == null) {
            variable = createVirtualVariable(element.getName());
            virtualVariables.getValue().put(element.getName(), variable);
        }
        return variable;
    }

    @SuppressWarnings("ConstantConditions")
    private KerboScriptNamedElement createVirtualVariable(String name) {
        KerboScriptFile file = (KerboScriptFile) PsiFileFactory.getInstance(getProject()).createFileFromText("undefined.ks", KerboScriptLanguage.INSTANCE,
                "local " + name + " to 0.", false, false);
        KerboScriptInstruction instruction = (KerboScriptInstruction) file.getFirstChild();
        return instruction.getDeclareStmt().getDeclareIdentifierClause();
    }

    private FileScope calcLocalDeclarations() {
        FileScope scope = new FileScope();
        for (PsiElement psiElement : this.getChildren()) {
            if (psiElement instanceof KerboScriptInstruction) {
                scope.addInstruction((KerboScriptInstruction) psiElement);
            }
        }
        return scope;
    }

    private boolean calcGlobalOff() {
        PsiElement child = getFirstChild();
        if (child instanceof KerboScriptInstruction) {
            KerboScriptDirective directive = ((KerboScriptInstruction) child).getDirective();
            if (directive != null) {
                KerboScriptLazyglobalDirective lazyglobal = directive.getLazyglobalDirective();
                return "off".equalsIgnoreCase(lazyglobal.getOnoffTrailer().getText());
            }
        }
        return false;
    }

    public KerboScriptFile resolveFile(KerboScriptNamedElement element) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, KerboScriptFileType.INSTANCE,
                GlobalSearchScope.allScope(getProject()));
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getName().toLowerCase().equals(element.getName() + ".ks")) {
                return (KerboScriptFile) PsiManager.getInstance(getProject()).findFile(virtualFile);
            }
        }
        return null;
    }

    private class FileScope extends LocalScope {
        private final ArrayList<KerboScriptFile> dependencies = new ArrayList<>();

        @Override
        public void addInstruction(KerboScriptInstruction instruction) {
            KerboScriptRunStmt runStmt = instruction.getRunStmt();
            if (runStmt!=null) {
                KerboScriptFile dependency = resolveFile(runStmt);
                if (dependency!=null) {
                    dependencies.add(dependency);
                }
            } else {
                super.addInstruction(instruction);
            }
        }
    }

    private enum Resolver {
        FUNCTION(FileScope::getFunction, KerboScriptFile::getVirtualFunction),
        VARIABLE(FileScope::getVariable, KerboScriptFile::getVirtualVariable);

        private final BiFunction<FileScope, String, KerboScriptNamedElement> findRegistered;
        private final BiFunction<KerboScriptFile, KerboScriptElementImpl, KerboScriptNamedElement> createVirtual;

        Resolver(BiFunction<FileScope, String, KerboScriptNamedElement> findRegistered,
                 BiFunction<KerboScriptFile, KerboScriptElementImpl, KerboScriptNamedElement> createVirtual) {
            this.findRegistered = findRegistered;
            this.createVirtual = createVirtual;
        }

        public PsiElement resolve(KerboScriptFile file,
                                  KerboScriptElementImpl element,
                                  HashSet<KerboScriptFile> visited) {
            PsiElement resolved = findRegistered.apply(file.scope.getValue(), element.getName());
            if (resolved == null) {
                boolean create = false;
                if (visited==null) {
                    create = true;
                    visited = new HashSet<>();
                }
                visited.add(file);
                for (KerboScriptFile dependency : file.scope.getValue().dependencies) {
                    if (!visited.contains(dependency)) {
                        resolved = this.resolve(dependency, element, visited);
                        if (resolved!=null) {
                            return resolved;
                        }
                    }
                }
                if (create) {
                    resolved = createVirtual.apply(file, element);
                }
            }
            return resolved;
        }

        public PsiElement resolve(KerboScriptFile file,
                                  KerboScriptElementImpl element) {
            return resolve(file, element, null);
        }

    }
}
