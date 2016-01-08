package ksp.kos.ideaplugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.util.IncorrectOperationException;
import ksp.kos.ideaplugin.reference.KerboScriptReference;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.Cache;
import ksp.kos.ideaplugin.reference.NamedType;
import ksp.kos.ideaplugin.reference.ReferenceType;
import ksp.kos.ideaplugin.reference.SuffixtermType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptNamedElementImpl extends KerboScriptElementImpl implements KerboScriptNamedElement {
    private final Cache<NamedScope> cache = new Cache<>(this, new NamedScope());

    public KerboScriptNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        if (isFile() || isFunction() || isVariable()) {
            ASTNode identifier = this.getNode().findChildByType(KerboScriptTypes.IDENTIFIER);
            if (identifier != null) {
                return identifier.getPsi();
            }
            identifier = this.getNode().findChildByType(KerboScriptTypes.FILEIDENT);
            if (identifier != null) {
                return identifier.getPsi();
            }
        }
        return null;
    }

    @Override
    public String getName() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            return nameIdentifier.getText();
        }
        return null;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier == null) {
            throw new IncorrectOperationException("Renaming of " + this + " is not possible: it's not true named element");
        }
        this.getNode().replaceChild(nameIdentifier.getNode(), Factory.createSingleLeafElement(KerboScriptTypes.IDENTIFIER, name, SharedImplUtil.findCharTableByTree(nameIdentifier.getNode()), getManager()));
        return this;
    }

    @Override
    public void setType(NamedType type) {
        cache.getScope().type = type;
        cache.getScope().reference = createReference(type);
        register();
    }

    private void register() {
        NamedType type = cache.getScope().type;
        if (type.getReferenceType().isDecaration()) {
            getScope().register(this);
        } else if (type.getType() == SuffixtermType.FILE) {
            getContainingFile().registerFile(this);
        }
    }

    @Override
    public NamedType getType() {
        return cache.getScope().type;
    }

    private KerboScriptReference createReference(NamedType type) {
        switch (type.getType()) {
            case FILE:
            case FUNCTION:
            case VARIABLE:
                return new KerboScriptReference(this);
        }
        return null;
    }

    @Override
    public KerboScriptReference getReference() {
        return cache.getScope().reference;
    }

    public PsiElement resolve(KerboScriptNamedElementImpl element) {
        if (element.getType().getReferenceType().isDecaration()) {
            return element;
        }
        if (element.isFunction()) {
            return getScope().resolveFunction(element);
        }
        if (element.isVariable()) {
            return getScope().resolveVariable(element);
        }
        if (element.isFile()) {
            return getContainingFile().resolveFile(element);
        }
        return null;
    }

    public boolean isFunction() {
        return getType().getType() == SuffixtermType.FUNCTION;
    }

    public boolean isVariable() {
        return getType().getType() == SuffixtermType.VARIABLE;
    }

    public boolean isFile() {
        return getType().getType() == SuffixtermType.FILE;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier!=null) return nameIdentifier;
        return super.getNavigationElement();
    }

    private class NamedScope {
        private NamedType type = new NamedType(SuffixtermType.OTHER, ReferenceType.NONE);
        private KerboScriptReference reference;
    }
}
