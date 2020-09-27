package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface FileDuality extends Duality {
    @NotNull KerboScriptFile getSyntax();
    FileContext getSemantics();
}
