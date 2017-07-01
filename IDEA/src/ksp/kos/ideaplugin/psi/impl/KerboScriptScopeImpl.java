package ksp.kos.ideaplugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import ksp.kos.ideaplugin.reference.Cache;
import ksp.kos.ideaplugin.reference.LocalScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptScopeImpl extends KerboScriptElementImpl implements KerboScriptScope {
    private final Cache<LocalScope> cache = new Cache<>(this, new LocalScope());

    public KerboScriptScopeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void register(KerboScriptNamedElement element) {
        getLocalScope().register(element);
    }

    @Override
    public PsiElement resolveFunction(KerboScriptNamedElement element) {
        KerboScriptNamedElement function = getLocalScope().resolveFunction(element);
        if (function==null) {
            return getScope().resolveFunction(element);
        }
        return function;
    }

    @Override
    public PsiElement resolveVariable(KerboScriptNamedElement element) {
        KerboScriptNamedElement variable = getLocalScope().resolveVariable(element);
        if (variable==null) {
            return getScope().resolveVariable(element);
        }
        return variable;
    }

    @Override
    public void clearCache() {
        getLocalScope().clear();
    }

    @NotNull
    private LocalScope getLocalScope() {
        return cache.getScope();
    }
}
