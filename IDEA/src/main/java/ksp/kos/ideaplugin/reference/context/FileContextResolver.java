package ksp.kos.ideaplugin.reference.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public interface FileContextResolver {
    @NotNull FileDuality ensureFile(String name);

    @Nullable FileDuality resolveFile(String name);
}
