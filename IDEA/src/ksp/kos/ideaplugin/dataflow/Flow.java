package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;

import java.util.HashMap;
import java.util.Set;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public interface Flow<F extends Flow<F>> {
    F differentiate();

    String getText();

    void addDependee(Flow<?> flow);

    void removeDependee(Flow<?> flow);

    F addContext(HashMap<String, NamedFlow<?>> context);
}
