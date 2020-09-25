package ksp.kos.ideaplugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * Created on 06/01/16.
 *
 * @author ptasha
 */
public class KerboScriptParserUtil extends GeneratedParserUtilBase {
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets) {
        ErrorState state = new ErrorState();
        ErrorState.initState(state, builder, root, extendsSets);
        // TODO - Look into remapping ident node tokens post-parsing, if possible.
        return new GeneratedParserUtilBase.Builder(builder, state, parser);
    }
}
