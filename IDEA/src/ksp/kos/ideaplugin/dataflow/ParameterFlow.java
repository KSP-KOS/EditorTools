package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.psi.KerboScriptDeclareParameterClause;

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
    public boolean addContext(Context context) {
        context.getMap().put(getName(), this);
        return true;
    }

    @Override
    public ParameterFlow differentiate(Context context) {
        context.add(this);
        ParameterFlow diff = differentiate();
        context.add(diff);
        return diff;
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
