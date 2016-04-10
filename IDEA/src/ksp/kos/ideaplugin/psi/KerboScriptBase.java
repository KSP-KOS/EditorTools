package ksp.kos.ideaplugin.psi;

import com.intellij.lang.ASTFactory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.KerboScriptFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptBase extends PsiElement {
    default KerboScriptFile getKerboScriptFile() {
        return (KerboScriptFile) getContainingFile();
    }

    default KerboScriptScope getScope() {
        PsiElement parent = getParent();
        while (!(parent instanceof KerboScriptScope)) {
            parent = parent.getParent();
        }
        return (KerboScriptScope) parent;
    }

    default <P extends PsiElement> P upTill(Class<P> clazz) {
        return PsiTreeUtil.getParentOfType(this, clazz, false);
    }

    default <P extends PsiElement> P downTill(Class<P> clazz) {
        return PsiTreeUtil.findChildOfType(this, clazz, false);
    }

    @NotNull
    default <P extends PsiElement> Collection<P> getChildren(Class<P> clazz) {
        return PsiTreeUtil.findChildrenOfType(this, clazz);
    }

    @NotNull
    default <T> CachedValue<T> createCachedValue(Supplier<T> supplier) {
        return createCachedValue(supplier, this);
    }

    @NotNull
    default <T> CachedValue<T> createCachedValue(Supplier<T> supplier, Object... dependencies) {
        return CachedValuesManager.getManager(getProject()).createCachedValue(
                () -> new CachedValueProvider.Result<>(
                        supplier.get(),
                        dependencies), false);
    }

    default void newLine() {
        getNode().addChild(ASTFactory.whitespace("\n"));
    }
}
