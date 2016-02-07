package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import ksp.kos.ideaplugin.KerboScriptFile;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptBaseElement extends PsiElement {
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

    @SuppressWarnings("unchecked")
    static <P> P walkUpTill(PsiElement element, Class<P> clazz) {
        while (element!=null) {
            if (clazz.isInstance(element)) {
                return (P) element;
            }
            element = element.getParent();
        }
        return null;
    }

    default <P> P walkUpTill(Class<P> clazz) {
        return walkUpTill(this, clazz);
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

}
