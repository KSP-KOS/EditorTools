package ksp.kos.ideaplugin.parser;

import com.intellij.lexer.FlexAdapter;

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
