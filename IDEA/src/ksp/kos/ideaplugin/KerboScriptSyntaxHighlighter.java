package ksp.kos.ideaplugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import ksp.kos.ideaplugin.parser.KerboScriptLexerAdapter;
import ksp.kos.ideaplugin.parser.KerboScriptParserDefinition;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 27/12/15.
 *
 * @author ptasha
 */
public class KerboScriptSyntaxHighlighter extends SyntaxHighlighterBase {
    private static final String PREFIX = "KOS.";
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(PREFIX + "KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey(PREFIX + "IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey(PREFIX + "COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(PREFIX + "STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(PREFIX + "NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey OPERATORS_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "OPERATOR_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey PARENTHESES_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACES_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);

    // It's intended since KOS using '.' as end of instruction and ':' as member.field separator.
    public static final TextAttributesKey EOI_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "DOT", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COLON_KEY = TextAttributesKey.createTextAttributesKey(PREFIX + "COLON", DefaultLanguageHighlighterColors.DOT);

    public static final TokenSet KEYWORDS = TokenSet.create(
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
                    KerboScriptTypes.IN,
                    KerboScriptTypes.ALL,
                    KerboScriptTypes.ATSIGN,
                    KerboScriptTypes.LAZYGLOBAL
    );

    public static final TokenSet OPERATORS = TokenSet.create(
            KerboScriptTypes.COMPARATOR,
            KerboScriptTypes.PLUSMINUS,
            KerboScriptTypes.MULT,
            KerboScriptTypes.DIV,
            KerboScriptTypes.POWER
    );
    public static final TokenSet NUMBERS = TokenSet.create(
            KerboScriptTypes.E,
            KerboScriptTypes.DOUBLE,
            KerboScriptTypes.INTEGER
    );

    // Proper names described here: https://www.cis.upenn.edu/~matuszek/General/JavaSyntax/parentheses.html
    // And here: https://en.wikipedia.org/wiki/Bracket
    public static final TokenSet PARENTHESES = TokenSet.create(
            KerboScriptTypes.BRACKETOPEN,
            KerboScriptTypes.BRACKETCLOSE
    );
    public static final TokenSet BRACES = TokenSet.create(
            KerboScriptTypes.CURLYOPEN,
            KerboScriptTypes.CURLYCLOSE
    );
    public static final TokenSet BRACKETS = TokenSet.create(
            KerboScriptTypes.SQUAREOPEN,
            KerboScriptTypes.SQUARECLOSE
    );


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new KerboScriptLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (KerboScriptTypes.IDENTIFIER.equals(tokenType)) {
            return pack(IDENTIFIER);
        } else if (KerboScriptParserDefinition.COMMENTS.contains(tokenType)) {
            return pack(COMMENT);
        } else if (KerboScriptTypes.STRING.equals(tokenType)) {
            return pack(STRING);
        } else if (KerboScriptTypes.EOI.equals(tokenType)) {
            return pack(EOI_KEY);
        } else if (KerboScriptTypes.COLON.equals(tokenType)) {
            return pack(COLON_KEY);
        } else if (KEYWORDS.contains(tokenType)) {
            return pack(KEYWORD);
        } else if (NUMBERS.contains(tokenType)) {
            return pack(NUMBER);
        } else if (OPERATORS.contains(tokenType)) {
            return pack(OPERATORS_KEY);
        } else if (PARENTHESES.contains(tokenType)) {
            return pack(PARENTHESES_KEY);
        } else if (BRACES.contains(tokenType)) {
            return pack(BRACES_KEY);
        } else if (BRACKETS.contains(tokenType)) {
            return pack(BRACKETS_KEY);
        }
        return new TextAttributesKey[0];
    }

}
