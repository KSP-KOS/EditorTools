package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.psi.KerboScriptDeclareParameterClause;

import java.util.HashMap;

/**
 * Created on 17/03/16.
 *
 * @author ptasha
 */
public class ParameterFlow extends BaseFlow<ParameterFlow> implements NamedFlow<ParameterFlow> {
    private final String name;

    public ParameterFlow(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addContext(HashMap<String, NamedFlow<?>> context) {
        context.put(getName(), this);
    }

    @Override
    public ParameterFlow differentiate() {
        return new ParameterFlow(name+"_");
    }

    @Override
    public String getText() {
        return "parameter "+name+".";
    }

    public static ParameterFlow parse(KerboScriptDeclareParameterClause parameterClause) {
        return new ParameterFlow(parameterClause.getName());
    }
}
