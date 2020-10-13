package ksp.kos.ideaplugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import ksp.kos.ideaplugin.parser.KerboScriptLexerAdapter;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 27/12/15.
 *
 * @author ptasha
 */
public class KerboScriptSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("NUMBER", DefaultLanguageHighlighterColors.NUMBER);

    public static final Set<IElementType> KEYWORDS = new HashSet<>(
            Arrays.asList(
                    KerboScriptTypes.NOT,
                    KerboScriptTypes.AND,
                    KerboScriptTypes.OR,
                    KerboScriptTypes.TRUEFALSE,
                    KerboScriptTypes.SET,
                    KerboScriptTypes.TO,
                    KerboScriptTypes.IS,
                    KerboScriptTypes.IF,
                    KerboScriptTypes.ELSE,
                    KerboScriptTypes.UNTIL,
                    KerboScriptTypes.STEP,
                    KerboScriptTypes.DO,
                    KerboScriptTypes.LOCK,
                    KerboScriptTypes.UNLOCK,
                    KerboScriptTypes.PRINT,
                    KerboScriptTypes.AT,
                    KerboScriptTypes.ON,
                    KerboScriptTypes.TOGGLE,
                    KerboScriptTypes.WAIT,
                    KerboScriptTypes.WHEN,
                    KerboScriptTypes.THEN,
                    KerboScriptTypes.OFF,
                    KerboScriptTypes.STAGE,
                    KerboScriptTypes.CLEARSCREEN,
                    KerboScriptTypes.ADD,
                    KerboScriptTypes.REMOVE,
                    KerboScriptTypes.LOG,
                    KerboScriptTypes.BREAK,
                    KerboScriptTypes.PRESERVE,
                    KerboScriptTypes.DECLARE,
                    KerboScriptTypes.DEFINED,
                    KerboScriptTypes.LOCAL,
                    KerboScriptTypes.GLOBAL,
                    KerboScriptTypes.PARAMETER,
                    KerboScriptTypes.FUNCTION,
                    KerboScriptTypes.RETURN,
                    KerboScriptTypes.SWITCH,
                    KerboScriptTypes.COPY,
                    KerboScriptTypes.FROM,
                    KerboScriptTypes.RENAME,
                    KerboScriptTypes.VOLUME,
                    KerboScriptTypes.FILE,
                    KerboScriptTypes.DELETE,
                    KerboScriptTypes.EDIT,
                    KerboScriptTypes.RUN,
                    KerboScriptTypes.ONCE,
                    KerboScriptTypes.COMPILE,
                    KerboScriptTypes.LIST,
                    KerboScriptTypes.REBOOT,
                    KerboScriptTypes.SHUTDOWN,
                    KerboScriptTypes.FOR,
                    KerboScriptTypes.UNSET,
                    KerboScriptTypes.CHOOSE,
                    KerboScriptTypes.ATSIGN,
                    KerboScriptTypes.LAZYGLOBAL
            )
    );

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new KerboScriptLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        // TODO - Ideally syntax highlight based off of parser, not lexer, to handle keywords used as variables.
        if (tokenType.equals(KerboScriptTypes.IDENTIFIER)) {
            return createKeys(IDENTIFIER);
        } else if (tokenType.equals(KerboScriptTypes.COMMENTLINE)) {
            return createKeys(COMMENT);
        } else if (tokenType.equals(KerboScriptTypes.STRING)) {
            return createKeys(STRING);
        } else if (KEYWORDS.contains(tokenType)) {
            return createKeys(KEYWORD);
        } else if (tokenType.equals(KerboScriptTypes.E) || tokenType.equals(KerboScriptTypes.INTEGER) || tokenType.equals(KerboScriptTypes.DOUBLE)) {
            return createKeys(NUMBER);
        }
        return new TextAttributesKey[0];
    }

    @NotNull
    private TextAttributesKey[] createKeys(TextAttributesKey identifier) {
        return new TextAttributesKey[]{identifier};
    }
}
