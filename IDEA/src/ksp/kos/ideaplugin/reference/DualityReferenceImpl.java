package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public class DualityReferenceImpl extends ReferenceImpl implements DualitySelfResolvable {
    public DualityReferenceImpl(LocalContext kingdom, ReferableType referableType, String name) {
        super(kingdom, referableType, name);
    }
}
