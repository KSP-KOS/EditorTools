package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.Reference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 10/10/16.
 *
 * @author ptasha
 */
public interface ReferenceResolver{
    @Nullable Duality resolve(LocalContext context, @NotNull Reference reference, boolean createAllowed);
}
