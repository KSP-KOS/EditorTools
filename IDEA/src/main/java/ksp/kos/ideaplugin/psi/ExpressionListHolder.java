package ksp.kos.ideaplugin.psi;

import java.util.List;

// TODO it's a kludge to deal with expression lists
public interface ExpressionListHolder extends ExpressionHolder {
    @Override
    default KerboScriptExpr getExpr() {
        return getExprList().get(0);
    }

    List<KerboScriptExpr> getExprList();
}
