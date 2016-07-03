package ksp.kos.ideaplugin.expressions.inline;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.HashMap;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.Function;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;
import org.apache.sanselan.util.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 07/03/16.
 *
 * @author ptasha
 */
public class InlineFunctions {
    private static final Logger LOG = Logger.getInstance(InlineFunctions.class);

    private static InlineFunctions instance;

    public static InlineFunctions getInstance() {
        if (instance == null) {
            instance = new InlineFunctions();
        }
        return instance;
    }

    private final HashMap<String, InlineFunction> functions = new HashMap<>();

    private InlineFunctions() {
        try {
            InputStream stream = this.getClass().getResourceAsStream("/inline.ks");
            if (stream != null) {
                String inline = new String(IOUtils.getInputStreamBytes(stream));
                KerboScriptFile file = KerboScriptElementFactory.file(inline);
                for (PsiElement child : file.getChildren()) {
                    if (child instanceof KerboScriptDeclareStmt) {
                        KerboScriptDeclareFunctionClause functionClause = ((KerboScriptDeclareStmt) child).getDeclareFunctionClause();
                        if (functionClause != null) {
                            InlineFunction function = parseFunction(functionClause);
                            if (function != null) {
                                functions.put(function.getName(), function);
                            }
                        }
                    }
                }
            } else {
                LOG.warn("inline.ks is not found");
            }
        } catch (Exception e) {
            LOG.warn("Failed to load inline.ks file", e);
        }
    }

    private InlineFunction parseFunction(KerboScriptDeclareFunctionClause function) {
        String name = function.getName();
        Expression expression = null;
        ArrayList<String> names = new ArrayList<>();
        List<KerboScriptInstruction> instructions = function.getInstructionBlock().getInstructionList();
        for (KerboScriptInstruction instruction : instructions) {
            if (instruction instanceof KerboScriptDeclareStmt) {
                KerboScriptDeclareStmt declareStmt = (KerboScriptDeclareStmt) instruction;
                KerboScriptDeclareParameterClause declareParameter = declareStmt.getDeclareParameterClause();
                if (declareParameter == null) {
                    LOG.warn("Failed to parse inline function " + name + ": unsupported declare statement " + declareStmt);
                    return null;
                }
                names.add(declareParameter.getName());
            } else if (instruction instanceof KerboScriptReturnStmt) {
                try {
                    expression = Expression.parse(((KerboScriptReturnStmt) instruction).getExpr());
                } catch (SyntaxException e) {
                    LOG.warn("Failed to parse inline function " + name, e);
                }
            } else {
                LOG.warn("Failed to parse inline function " + name + ": unsupported instruction " + instruction);
                return null;
            }
        }
        if (expression == null) {
            LOG.warn("Failed to parse inline function " + name + ": return statement is not found");
            return null;
        }
        return new InlineFunction(name, names.toArray(new String[names.size()]), expression);
    }

    public Expression inline(Function function) {
        InlineFunction inlineFunction = functions.get(function.getName());
        if (inlineFunction == null) {
            return function;
        }
        return inlineFunction.inline(function.getArgs());
    }
}
