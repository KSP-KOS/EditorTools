package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.Cache;
import ksp.kos.ideaplugin.reference.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptScopeImpl extends ASTWrapperPsiElement implements KerboScriptScope {
    private Cache<Context<KerboScriptNamedElement>> cache;

    public KerboScriptScopeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public synchronized Context<KerboScriptNamedElement> getCachedScope() {
        if (cache==null) {
            cache = new Cache<>(this, new Context<KerboScriptNamedElement>(this.getScope().getCachedScope()));
        }
        return cache.getScope();
    }
}
