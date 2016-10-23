package ksp.kos.ideaplugin.dataflow;

import com.intellij.psi.PsiFile;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.expressions.*;
import ksp.kos.ideaplugin.expressions.inline.InlineFunction;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.*;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public class FunctionFlow extends BaseFlow<FunctionFlow> implements NamedFlow<FunctionFlow>, ReferenceFlow {
    private final FileContext file; // TODO replace file to FileContext
    private final String name;

    private final ContextBuilder parameters;
    private final ContextBuilder instructions;

    public FunctionFlow(FileContext file, String name, ContextBuilder parameters, ContextBuilder instructions) {
        this.file = file;
        this.name = name;
        this.parameters = parameters;
        this.instructions = instructions;
        parameters.buildMap();
        instructions.buildMap();
        instructions.getReturn().addDependee(this);
    }

    public static FunctionFlow parse(KerboScriptDeclareFunctionClause function) throws SyntaxException {
        FunctionParser parser = new FunctionParser();
        String name = function.getName();
        parser.parseInstructions(function.getInstructionBlock().getInstructionList());
        return new FunctionFlow(function.getKerboScriptFile().getCachedScope(), name, parser.getContext(), parser.getFlowContext());
        // throw new SyntaxException("Return statement is not found for function " + name); // TODO check for return statement
    }

    @Override
    public FunctionFlow differentiate(LocalContext context) {
        ContextBuilder parameters = new ContextBuilder();
        this.parameters.differentiate(context, parameters); // TODO single parameter
        ContextBuilder flows = new ContextBuilder(parameters);
        this.instructions.differentiate(context, flows);

        return new FunctionFlow(file, name + "_", parameters, flows).simplify();
    }

    private FunctionFlow simplify() {
        instructions.simplify();
        parameters.simplify(); // TODO teach Function to deal with it

        return this;
    }

    @Override
    public String getText() {
        String text = "";
        text += "function " + name + " {\n";
        text += parameters.getText() + "\n";
        if (!parameters.getList().isEmpty()) text += "\n";
        text += instructions.getText() + "\n";
        text += "}";
        return text;
    }

    @Override
    public LocalContext getKingdom() {
        return null; // TODO implement me
    }

    @Override
    public ReferableType getReferableType() {
        return ReferableType.FUNCTION;
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<ImportFlow> getImports(FileContext newFile) {
        HashSet<ImportFlow> imports = new HashSet<>();
        HashSet<String> context = new HashSet<>();
        visitExpresssions(new ExpressionVisitor() {
            @Override
            public void visitFunction(Function function) {
                DualitySelfResolvable ref = DualitySelfResolvable.function(file, function.getName());
                addImport(findFunction(ref));
                super.visitFunction(function);
            }

            @Override
            public void visitVariable(Variable variable) {
                String name = variable.getName();
                if (!context.contains(name)) {
                    addImport(DualitySelfResolvable.variable(file, name).findDeclaration());
                }
                context.add(name);
                super.visitVariable(variable);
            }

            private void addImport(Duality resolved) {
                if (resolved != null) {
                    FileContext dependency = resolved.getKingdom().getFileContext();
                    if (dependency != newFile) {
                        imports.add(new ImportFlow(dependency));
                    }
                }
            }
        });
        return imports;
    }

    public void visitExpresssions(ExpressionVisitor visitor) {
        parameters.visit(visitor);
        instructions.visit(visitor);
    }

    private Duality findFunction(DualitySelfResolvable ref) {
        Duality resolved = ref.findDeclaration();
        if (resolved!=null) {
            return resolved;
        }
        String name = ref.getName();
        if (name.endsWith("_")) {
            while (name.endsWith("_")) {
                name = name.substring(0, name.length() - 1);
            }
            KerboScriptNamedElement orig = PsiSelfResolvable.function(ref.getKingdom(), name).findDeclaration();
            if (orig != null) {
                PsiFile psiFile = orig.getContainingFile();
                if (psiFile instanceof KerboScriptFile) {
                    String fileName = ((KerboScriptFile) psiFile).getPureName();
                    if (!fileName.endsWith("_")) {
                        Duality<KerboScriptFile, FileContext> diffFile =  DualitySelfResolvable.file(this.file, fileName + "_").findDeclaration();
                        if (diffFile!=null) {
                            return DualitySelfResolvable.function(diffFile.getSemantics(), ref.getName()).findDeclaration();
                        }
                    }
                }
            }
        }
        return null;
    }

    public Duality getNextToDiff(Map<? extends Reference, FunctionFlow> context) {
        AtomicReference<Duality> reference = new AtomicReference<>();
        visitExpresssions(new ExpressionVisitor() {
            @Override
            public void visitFunction(Function function) {
                if (reference.get() == null) {
                    String name = function.getName();
                    DualitySelfResolvable ref = DualitySelfResolvable.function(file, name);
                    Duality declaration = findFunction(ref);
                    if (declaration == null) {
                        reference.set(undiff(ref));
                    }
                    if (reference.get()==null) {
                        super.visitFunction(function);
                    }
                }
            }

            @Nullable
            private Duality undiff(DualitySelfResolvable reference) {
                String name = reference.getName();
                if (name.endsWith("_")) {
                    name = name.substring(0, name.length() - 1);
                    DualitySelfResolvable undiff = DualitySelfResolvable.function(reference.getKingdom(), name);
                    if (!context.containsKey(undiff)) {
                        Duality resolved = findFunction(undiff);
                        if (resolved != null) {
                            return resolved;
                        } else {
                            return undiff(undiff);
                        }
                    } else {
                        return null;
                    }
                }
                return null;
            }

            @Override
            public void visit(Expression expression) {
                if (reference.get() == null) {
                    super.visit(expression);
                }
            }
        });
        return reference.get();
    }

    public InlineFunction getInlineFunction() {
        MixedDependency dependency = instructions.getReturn();
        ReturnFlow flow = dependency.getReturnFlow();
        if (flow != null) {
            Expression expression = flow.getExpression();
            if (flow.isSimple()) {
                return inline(expression);
            } else if (expression instanceof Function) {
                Function function = (Function) expression;
                if (function.getArgs().length == 0) {
                    return inline(function);
                } else if (function.getArgs().length == 1) {
                    Expression arg = function.getArgs()[0];
                    if (arg instanceof Variable) {
                        Variable variable = (Variable) arg;
                        if (parameters.getFlow(variable.getName()) != null) {
                            return inline(function);
                        }
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    private InlineFunction inline(Expression expression) {
        return new InlineFunction(name, getArgNames(), expression);
    }

    private String[] getArgNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Flow parameter : parameters.getList()) {
            names.add(((ParameterFlow)parameter).getName());
        }
        return names.toArray(new String[names.size()]);
    }

    private static class FunctionParser extends InstructionsParser {
        private final InstructionsParser parser = new InstructionsParser(getContext());
        private boolean parametersParsed = false;

        @Override
        public boolean parseInstruction(KerboScriptInstruction instruction) throws SyntaxException {
            if (parametersParsed) {
                return parser.parseInstruction(instruction);
            } else if (instruction instanceof KerboScriptDeclareStmt) {
                KerboScriptDeclareStmt declareStmt = (KerboScriptDeclareStmt) instruction;
                KerboScriptDeclareParameterClause declareParameterClause = declareStmt.getDeclareParameterClause();
                if (declareParameterClause != null) {
                    addFlow(ParameterFlow.parse(declareParameterClause));
                } else if (declareStmt.getDeclareIdentifierClause() != null) {
                    parametersParsed = true;
                    return parser.parseInstruction(instruction);
                }
            } else {
                parametersParsed = true;
                return parser.parseInstruction(instruction);
            }
            return true;
        }

        public ContextBuilder getFlowContext() {
            return parser.getContext();
        }
    }
}
