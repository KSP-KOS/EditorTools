package ksp.kos.ideaplugin.reference;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public enum SuffixtermType {
    VARIABLE,
    FUNCTION,
    FIELD,
    METHOD,
    FILE,
    OTHER;

    private static final Set<SuffixtermType> REFERABLE = EnumSet.of(VARIABLE, FILE, FUNCTION);

    public boolean isReferable() {
        return REFERABLE.contains(this);
    }
}
