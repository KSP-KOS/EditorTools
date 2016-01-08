package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptElement extends PsiElement {
    KerboScriptScope getScope();

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
