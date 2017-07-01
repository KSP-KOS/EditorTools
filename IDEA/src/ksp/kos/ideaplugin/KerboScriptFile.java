package ksp.kos.ideaplugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.problems.WolfTheProblemSolver;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScopesCore;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.indexing.FileBasedIndex;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptPsiWalker;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.Cache;
import ksp.kos.ideaplugin.reference.FileScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFile extends PsiFileBase implements KerboScriptScope {
    private final Cache<FileScope> cache = new Cache<>(this, new FileScope(this));

    public KerboScriptFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, KerboScriptLanguage.INSTANCE);
        DumbService.getInstance(getProject()).runWhenSmart(() -> {
            VirtualFile virtualFile = getVirtualFile();
            if (virtualFile != null) {
                WolfTheProblemSolver wolf = WolfTheProblemSolver.getInstance(getProject());
                wolf.queue(virtualFile);
            }
        });
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

    @Override
    public void register(KerboScriptNamedElement element) {
        getFileScope().register(element);
    }

    @Override
    public PsiElement resolveFunction(KerboScriptNamedElement element) {
        return FileScope.Resolver.FUNCTION.resolve(this, element);
    }

    @Override
    public PsiElement resolveVariable(KerboScriptNamedElement element) {
        return FileScope.Resolver.VARIABLE.resolve(this, element);
    }

    public void registerFile(KerboScriptNamedElement element) {
        getFileScope().addDependency(element);
    }

    public KerboScriptFile resolveFile(KerboScriptNamedElement element) {
        PsiDirectory directory = getContainingDirectory();
        if (directory == null) return null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, KerboScriptFileType.INSTANCE,
                GlobalSearchScopesCore.directoryScope(directory, false));
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getName().equalsIgnoreCase(element.getName() + ".ks")) {
                return (KerboScriptFile) PsiManager.getInstance(getProject()).findFile(virtualFile);
            }
        }
        return null;
    }

    @Override
    public void clearCache() {
        getFileScope().clear();
    }

    private long version = -1;
    private final ReentrantLock cacheLock = new ReentrantLock();

    public void checkVersion() {
        if (!cacheLock.isHeldByCurrentThread()) {
            if (cacheLock.tryLock()) {
                try {
                    checkVersionInternal();
                } finally {
                    cacheLock.unlock();
                }
            } else {
                cacheLock.lock();
                try {
                    checkVersionInternal();
                } finally {
                    cacheLock.unlock();
                }
            }
        }
    }

    private void checkVersionInternal() {
        if (version != getModificationStamp()) {
            clearCache();
            this.accept(new KerboScriptPsiWalker());
            version = getModificationStamp();
        }
    }

    public FileScope getFileScope() {
        return cache.getScope();
    }

    @Override
    public KerboScriptScope getScope() {
        return this;
    }

}
