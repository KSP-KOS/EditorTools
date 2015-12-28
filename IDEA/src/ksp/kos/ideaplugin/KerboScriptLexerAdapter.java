package ksp.kos.ideaplugin;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptLexerAdapter extends FlexAdapter {
    public KerboScriptLexerAdapter() {
        super(new KerboScriptLexer(null));
    }
}
