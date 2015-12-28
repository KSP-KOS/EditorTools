package ksp.kos.ideaplugin;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFileType extends LanguageFileType {
    public static final KerboScriptFileType INSTANCE = new KerboScriptFileType(KerboScriptLanguage.INSTANCE);

    /**
     * Creates a language file type for the specified language.
     *
     * @param language The language used in the files of the type.
     */
    public KerboScriptFileType(@NotNull Language language) {
        super(language);
    }

    @NotNull
    @Override
    public String getName() {
        return "KerboScript";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "KerboScript";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "ks";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
