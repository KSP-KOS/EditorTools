package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.Cache;
import ksp.kos.ideaplugin.reference.LocalScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptScopeImpl extends ASTWrapperPsiElement implements KerboScriptScope {
    private final Cache<LocalScope> cache = new Cache<>(this, new LocalScope(this.getScope().getCachedScope()));

    public KerboScriptScopeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public LocalScope getCachedScope() {
        return cache.getScope();
    }
}
