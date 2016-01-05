package ksp.kos.ideaplugin;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import ksp.kos.ideaplugin.psi.impl.KerboScriptElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public class KerboScriptReference extends PsiReferenceBase<KerboScriptElementImpl> {
    public KerboScriptReference(@NotNull KerboScriptElementImpl element) {
        super(element);
    }

    @Override
    protected TextRange calculateDefaultRangeInElement() {
        PsiElement nameIdentifier = myElement.getNameIdentifier();
        if (nameIdentifier==null) {
            return myElement.getTextRange().shiftRight(-myElement.getTextOffset());
        }
        return nameIdentifier.getTextRange().shiftRight(-myElement.getTextOffset());
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return myElement.resolve(myElement);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return myElement.setName(newElementName);
    }
}
