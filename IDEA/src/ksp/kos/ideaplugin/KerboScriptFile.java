package ksp.kos.ideaplugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.problems.WolfTheProblemSolver;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptPsiWalker;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFile extends PsiFileBase implements KerboScriptScope, KerboScriptNamedElement {
    private static final ReferenceType TYPE = new ReferenceType(ReferableType.FILE, OccurrenceType.GLOBAL);
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

    public KerboScriptNamedElement findFunction(String name) {
        return getCachedScope().findDeclaration(Reference.function(this, name));
    }

    public KerboScriptNamedElement findVariable(String name) {
        return getCachedScope().findDeclaration(Reference.variable(this, name));
    }

    public KerboScriptFile resolveFile(String name) {
        return findFile(stripExtension(name));
    }

    @Nullable
    public KerboScriptFile findFile(String name) {
        PsiDirectory directory = getContainingDirectory();
        if (directory == null) return null;
        for (PsiFile file : directory.getFiles()) {
            if (file instanceof KerboScriptFile) {
                if (((KerboScriptFile) file).getPureName().equals(name)) {
                    return (KerboScriptFile) file;
                }
            }
        }
        return null;
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
            getCachedScope().clear();
            this.accept(new KerboScriptPsiWalker());
            version = getModificationStamp();
        }
    }

    @Override
    public FileScope getCachedScope() {
        return cache.getScope();
    }

    @Override
    public KerboScriptScope getScope() {
        return this;
    }

    public static String getExtension(String name) {
        if (name.endsWith(".ks")) {
            return ".ks";
        } else if (name.endsWith(".ksm")) {
            return ".ksm";
        }
        return "";
    }

    public static String stripExtension(String name) {
        String extension = getExtension(name);
        return name.substring(0, name.length() - extension.length());
    }

    @NotNull
    public String getPureName() {
        return stripExtension(getName());
    }

    @Override
    public ReferenceType getType() {
        return TYPE;
    }

    @Override
    public void setType(ReferenceType type) {
    }
}
