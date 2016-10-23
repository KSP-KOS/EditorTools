package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.reference.PsiFileResolver;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.ideaplugin.reference.context.FileDuality;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public class DiffContext extends FileContext {
    public DiffContext(FileContext origFile, PsiFileResolver fileResolver) {
        super(null, origFile.getName()+"_", FileContext.createResolvers(fileResolver));
        for (String name : origFile.getImports().keySet()) {
            registerFile(fileResolver, name);
            registerFile(fileResolver, name+"_");
        }
        registerFile(fileResolver, origFile.getName());
    }

    private void registerFile(PsiFileResolver fileResolver, String name) {
        FileDuality file = fileResolver.resolveFile(name);
        if (file!=null) {
            register(file);
        }
    }
}
