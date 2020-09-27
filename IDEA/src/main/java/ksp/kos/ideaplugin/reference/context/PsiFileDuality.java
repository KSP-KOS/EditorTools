package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class PsiFileDuality extends PsiDuality implements FileDuality {
    public PsiFileDuality(KerboScriptFile psi) {
        super(psi);
    }

    @Override
    public @NotNull KerboScriptFile getSyntax() {
        return (KerboScriptFile) super.getSyntax();
    }

    @Override
    public FileContext getSemantics() {
        return (FileContext) super.getSemantics();
    }
}
