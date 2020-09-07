package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public class PsiReferenceImpl extends ReferenceImpl implements PsiSelfResolvable {
    public PsiReferenceImpl(LocalContext kingdom, ReferableType referableType, String name) {
        super(kingdom, referableType, name);
    }
}
