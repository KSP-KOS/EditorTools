package ksp.kos.ideaplugin.reference;

import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.impl.KerboScriptNamedElementImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 01/04/16.
 *
 * @author ptasha
 */
public class KerboScriptFileReference extends KerboScriptReference {
    public KerboScriptFileReference(@NotNull KerboScriptNamedElementImpl element) {
        super(element);
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        newElementName = KerboScriptFile.stripExtension(newElementName.substring(0, newElementName.length() - 3));
        newElementName += KerboScriptFile.getExtension(myElement.getName());
        return super.handleElementRename(newElementName);
    }
}
