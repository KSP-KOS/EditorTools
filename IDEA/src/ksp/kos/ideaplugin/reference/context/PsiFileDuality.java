package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class PsiFileDuality extends PsiDuality<KerboScriptFile, FileContext> implements FileDuality {
    public PsiFileDuality(KerboScriptFile psi) {
        super(psi);
    }
}
