package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;

import java.util.Objects;

/**
 * Created on 05/04/16.
 *
 * @author ptasha
 */
public class ImportFlow extends BaseFlow<ImportFlow> implements NamedFlow<ImportFlow>, Comparable<ImportFlow> {
    private final String name;

    public ImportFlow(String name) {
        this.name = name;
    }

    public ImportFlow(KerboScriptFile file) {
        this(file.getPureName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ImportFlow differentiate() {
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
    public int compareTo(ImportFlow o) {
        return getName().compareTo(o.getName());
    }
}
