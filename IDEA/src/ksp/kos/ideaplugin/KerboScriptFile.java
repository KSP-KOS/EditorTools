package ksp.kos.ideaplugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFile extends PsiFileBase {
    protected KerboScriptFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, KerboScriptLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return KerboScriptFileType.INSTANCE;
    }
}
