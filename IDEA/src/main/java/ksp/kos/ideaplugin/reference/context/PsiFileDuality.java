package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;

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
    public KerboScriptFile getSyntax() {
        return (KerboScriptFile) super.getSyntax();
    }

    @Override
    public FileContext getSemantics() {
        return (FileContext) super.getSemantics();
    }
}
