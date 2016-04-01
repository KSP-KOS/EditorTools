package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import ksp.kos.ideaplugin.reference.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptNamedElementImpl extends ASTWrapperPsiElement implements KerboScriptNamedElement {
    private final Cache<NamedScope> cache = new Cache<>(this, new NamedScope());

    public KerboScriptNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        if (getType().getType().isReferable()) {
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
    public PsiNamedElement setName(@NonNls @NotNull String name) {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier!=null) {
            this.getNode().replaceChild(nameIdentifier.getNode(), KerboScriptElementFactory.leaf(KerboScriptTypes.IDENTIFIER, name));
        }
        return this;
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
            getKerboScriptFile().registerFile(this);
        }
    }

    @Override
    public NamedType getType() {
        return cache.getScope().type;
    }

    private KerboScriptReference createReference(NamedType type) {
        switch (type.getType()) {
            case FILE:
                return new KerboScriptFileReference(this);
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
            return getKerboScriptFile().resolveFile(element);
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
