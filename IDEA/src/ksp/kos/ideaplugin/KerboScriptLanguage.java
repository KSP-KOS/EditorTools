package ksp.kos.ideaplugin;

import com.intellij.lang.Language;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptLanguage extends Language {
    public static final KerboScriptLanguage INSTANCE = new KerboScriptLanguage();

    public KerboScriptLanguage() {
        super("KerboScript");
    }
}
