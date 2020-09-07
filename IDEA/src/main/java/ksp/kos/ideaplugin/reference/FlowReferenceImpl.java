package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class FlowReferenceImpl extends ReferenceImpl implements FlowSelfResolvable {
    public FlowReferenceImpl(LocalContext kingdom, ReferableType referableType, String name) {
        super(kingdom, referableType, name);
    }
}
