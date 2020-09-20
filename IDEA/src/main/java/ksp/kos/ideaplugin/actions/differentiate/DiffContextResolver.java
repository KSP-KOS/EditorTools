package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.reference.PsiFileResolver;
import ksp.kos.ideaplugin.reference.context.FileContextResolver;
import ksp.kos.ideaplugin.reference.context.FileDuality;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created on 27/10/16.
 *
 * @author ptasha
 */
public class DiffContextResolver implements FileContextResolver {
    private final PsiFileResolver fileResolver;
    private final HashMap<String, DiffContext> contexts = new HashMap<>();

    public DiffContextResolver(PsiFileResolver fileResolver) {
        this.fileResolver = fileResolver;
    }

    public void importFlows() {
        for (DiffContext diffContext : contexts.values()) {
            diffContext.checkUsage();
        }
        for (DiffContext diffContext : contexts.values()) {
            diffContext.importFlows();
        }
    }

    @Override
    public @NotNull FileDuality ensureFile(String name) {
        return fileResolver.ensureFile(name);
    }

    @Override
    public FileDuality resolveFile(String name) {
        DiffContext context = contexts.get(name);
        if (context!=null) {
            return context;
        } else if (name.endsWith("__")) {
            return resolveFile(name.substring(0, name.length() - 1));
        } else if (name.endsWith("_")) {
            context = new DiffContext(fileResolver.resolveFile(name.substring(0, name.length() - 1)).getSemantics(),
                    fileResolver.resolveFile(name),
                    this);
            contexts.put(name, context);
            return context;
        } else {
            return fileResolver.resolveFile(name);
        }
    }
}
