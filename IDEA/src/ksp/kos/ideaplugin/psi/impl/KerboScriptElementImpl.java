package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.psi.util.CachedValue;
import com.intellij.util.IncorrectOperationException;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.KerboScriptReference;
import ksp.kos.ideaplugin.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public class KerboScriptElementImpl extends ASTWrapperPsiElement implements KerboScriptNamedElement {
    private final CachedValue<KerboScriptReference> reference = createCachedValue(this::calcReference);
    private final CachedValue<LocalScope> scope = createCachedValue(this::calcLocalDeclarations);
    private final CachedValue<NamedType> type = createCachedValue(this::calcType);

    public KerboScriptElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getName() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            return nameIdentifier.getText().toLowerCase();
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

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        if (!isReferenced()) return null;
        return getNameIdentifierInternal();
    }

    @Nullable
    private PsiElement getNameIdentifierInternal() {
        ASTNode identifier = this.getNode().findChildByType(KerboScriptTypes.IDENTIFIER);
        if (identifier != null) {
            return identifier.getPsi();
        }
        identifier = this.getNode().findChildByType(KerboScriptTypes.FILEIDENT);
        if (identifier != null) {
            return identifier.getPsi();
        }
        return null;
    }

    @Override
    public KerboScriptReference getReference() {
        return reference.getValue();
    }

    @Override
    public boolean isLocal() {
        return type.getValue().getReferenceType()==ReferenceType.LOCAL;
    }

    public boolean isFunction() {
        return type.getValue().getType()==SuffixtermType.FUNCTION;
    }

    public boolean isVariable() {
        return type.getValue().getType()==SuffixtermType.VARIABLE;
    }

    public boolean isFile() {
        return type.getValue().getType()==SuffixtermType.FILE;
    }

    private boolean isDeclaration() {
        switch (type.getValue().getReferenceType()) {
            case GLOBAL:
            case LOCAL:
                return true;
        }
        return false;
    }

    @Override
    public KerboScriptFile getContainingFile() {
        return (KerboScriptFile) super.getContainingFile();
    }

    public PsiElement resolve(KerboScriptElementImpl element) {
        if (element.isDeclaration()) {
            return element;
        }
        if (element.isFunction()) {
            return resolveFunction(element);
        }
        if (element.isVariable()) {
            return resolveVariable(element);
        }
        if (element.isFile()) {
            return getContainingFile().resolveFile(element);
        }
        return null;
    }

    @Override
    public PsiElement getContext() {
        PsiElement parent = getParent();
        if (parent!=null && !isContext(parent)) {
            return parent.getContext();
        }
        return parent;
    }

    private static boolean isContext(PsiElement element) {
        return element instanceof KerboScriptInstructionBlock || element instanceof KerboScriptFile;
    }

    @Nullable
    protected PsiElement resolveFunction(KerboScriptElementImpl element) {
        String name = element.getName();
        if (name==null) return null;
        if (this instanceof KerboScriptInstructionBlock) {
            KerboScriptDeclareFunctionClause declareFunctionClause = getFunction(name);
            if (declareFunctionClause!=null) {
                return declareFunctionClause;
            }
        }
        PsiElement context = this.getContext();
        if (context instanceof KerboScriptInstructionBlockImpl) {
            return ((KerboScriptInstructionBlockImpl) context).resolveFunction(element);
        } else if (context instanceof KerboScriptFile) {
            return ((KerboScriptFile)context).resolveFunction(element);
        }
        return null;
    }

    private KerboScriptDeclareFunctionClause getFunction(String name) {
        return scope.getValue().getFunction(name);
    }

    protected PsiElement resolveVariable(KerboScriptElementImpl element) {
        String name = element.getName();
        if (name==null) return null;
        if (this instanceof KerboScriptInstructionBlock) {
            KerboScriptNamedElement declareClause = getVariable(name);
            if (declareClause!=null && declareClause.getTextOffset() < element.getTextOffset()) {
                return declareClause;
            }
        }
        PsiElement context = this.getContext();
        if (context instanceof KerboScriptInstructionBlockImpl) {
            return ((KerboScriptInstructionBlockImpl) context).resolveVariable(element);
        } else if (context instanceof KerboScriptFile) {
            return ((KerboScriptFile)context).resolveVariable(element);
        }
        return null;
    }

    private KerboScriptNamedElement getVariable(String name) {
        return scope.getValue().getVariable(name);
    }

    private LocalScope calcLocalDeclarations() {
        LocalScope localScope = new LocalScope();
        if (this instanceof KerboScriptInstructionBlock) {
            ((KerboScriptInstructionBlock) this).getInstructionList().forEach(localScope::addInstruction);
        }
        return localScope;
    }

    private NamedType calcType() {
        if (this instanceof KerboScriptDeclareFunctionClause) {
            return new NamedType(SuffixtermType.FUNCTION, ReferenceType.LOCAL);
        }
        if (this instanceof KerboScriptDeclareParameterClause) {
            return new NamedType(SuffixtermType.VARIABLE, ReferenceType.LOCAL);
        }
        if (this instanceof KerboScriptDeclareIdentifierClause) {
            PsiElement parent = this.getParent();
            if (parent.getNode().findChildByType(KerboScriptTypes.GLOBAL)!=null) {
                return new NamedType(SuffixtermType.VARIABLE, ReferenceType.GLOBAL);
            } else {
                return new NamedType(SuffixtermType.VARIABLE, ReferenceType.LOCAL);
            }
        }

        if (this instanceof KerboScriptAtom) {
            if (this.getNameIdentifierInternal()==null) {
                return new NamedType(SuffixtermType.OTHER, ReferenceType.NONE);
            }
            PsiElement parent = this.getParent();
            if (parent instanceof KerboScriptSuffixterm) {
                List<KerboScriptSuffixtermTrailer> list = ((KerboScriptSuffixterm)parent).getSuffixtermTrailerList();
                if (list.isEmpty()) { // this is variable or field
                    if (parent.getParent() instanceof KerboScriptSuffixTrailer) {
                        return new NamedType(SuffixtermType.FIELD, ReferenceType.REFERENCE);
                    } else {
                        return new NamedType(SuffixtermType.VARIABLE, ReferenceType.REFERENCE);
                    }
                } else {
                    KerboScriptSuffixtermTrailer first = list.get(0);
                    if (first.getFunctionTrailer()!=null) { // this is function of method call
                        PsiElement parentParent = parent.getParent();
                        if (parentParent instanceof KerboScriptSuffixTrailer) {
                            return new NamedType(SuffixtermType.METHOD, ReferenceType.REFERENCE);
                        } else {
                            return new NamedType(SuffixtermType.FUNCTION, ReferenceType.REFERENCE);
                        }
                    } else { // this is array
                        if (parent.getParent() instanceof KerboScriptSuffixTrailer) {
                            return new NamedType(SuffixtermType.FIELD, ReferenceType.REFERENCE);
                        } else {
                            return new NamedType(SuffixtermType.VARIABLE, ReferenceType.REFERENCE);
                        }
                    }
                }
            }
        }
        if (this instanceof KerboScriptRunStmt) {
            return new NamedType(SuffixtermType.FILE, ReferenceType.REFERENCE);
        }
        return new NamedType(SuffixtermType.OTHER, ReferenceType.NONE);
    }

    @Nullable
    private KerboScriptReference calcReference() {
        if (isDeclaration() || isReferenced()) {
            return new KerboScriptReference(this);
        }
        return null;
    }

    private boolean isReferenced() {
        return isFunction() || isVariable() || isFile();
    }

}
