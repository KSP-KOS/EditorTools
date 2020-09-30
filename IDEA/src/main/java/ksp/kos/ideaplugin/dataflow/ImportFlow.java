package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Created on 05/04/16.
 *
 * @author ptasha
 */
public class ImportFlow extends BaseFlow<ImportFlow>
        implements NamedFlow<ImportFlow>, Comparable<ImportFlow>,
        ReferenceFlow<ImportFlow>, Duality {
    private final String name;

    public ImportFlow(String name) {
        this.name = name;
    }

    @Override
    public LocalContext getKingdom() {
        return null; // TODO implement
    }

    @Override
    public ReferableType getReferableType() {
        return ReferableType.FILE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ImportFlow differentiate(LocalContext context) {
        if (name.endsWith("_")) {
            return this;
        }
        return new ImportFlow(name+"_");
    }

    @Override
    public String getText() {
        return "run once "+getName()+".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportFlow that = (ImportFlow) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull ImportFlow o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public @Nullable KerboScriptNamedElement getSyntax() {
        return null; // TODO implement
    }

    @Override
    public ImportFlow getSemantics() {
        return this;
    }
}
