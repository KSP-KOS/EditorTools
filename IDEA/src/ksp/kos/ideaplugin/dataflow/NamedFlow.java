package ksp.kos.ideaplugin.dataflow;

import java.util.Objects;

/**
 * Created on 18/03/16.
 *
 * @author ptasha
 */
public interface NamedFlow<F extends NamedFlow<F>> extends Flow<F> {
    String getName();
}
