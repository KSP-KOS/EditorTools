package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
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
public class KerboScriptNamedElementImpl extends ASTWrapperPsiElement implements KerboScriptNamedElement, PsiNameIdentifierOwner {
    private final Cache<NamedScope> cache = new Cache<>(this, new NamedScope());

    public KerboScriptNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        if (getReferableType().isReferable()) {
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
    public void setType(ReferenceType type) {
        cache.getScope().type = type;
        cache.getScope().reference = createReference(type);
        register();
    }

    private void register() {
        ReferenceType type = cache.getScope().type;
        if (type.getOccurrenceType().isDeclaration()) {
            getScope().getCachedScope().register(this);
        } else if (type.getType() == ReferableType.FILE) {
            getScope().getCachedScope().register(this);
        }
    }

    @Override
    public ReferenceType getType() {
        return cache.getScope().type;
    }

    private KerboScriptReference createReference(ReferenceType type) {
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

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier!=null) return nameIdentifier;
        return super.getNavigationElement();
    }

    private class NamedScope {
        private ReferenceType type = new ReferenceType(ReferableType.OTHER, OccurrenceType.NONE);
        private KerboScriptReference reference;
    }
}
