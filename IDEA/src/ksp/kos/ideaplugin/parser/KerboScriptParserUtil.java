package ksp.kos.ideaplugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderAdapter;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Created on 06/01/16.
 *
 * @author ptasha
 */
public class KerboScriptParserUtil extends GeneratedParserUtilBase {
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets) {
        builder = new RemapperPsiBuilder(builder);
        return GeneratedParserUtilBase.adapt_builder_(root, builder, parser, extendsSets);
    }

    public static boolean nextTokenIs(PsiBuilder builder, IElementType token) {
        advise(builder, token);
        return GeneratedParserUtilBase.nextTokenIs(builder, token);
    }

    public static boolean nextTokenIs(PsiBuilder builder, String frameName, IElementType... tokens) {
        advise(builder, tokens);
        return GeneratedParserUtilBase.nextTokenIs(builder, frameName, tokens);
    }

    public static boolean consumeToken(PsiBuilder builder, IElementType token) {
        advise(builder, token);
        return GeneratedParserUtilBase.consumeToken(builder, token);
    }

    public static boolean consumeTokens(PsiBuilder builder, int pin, IElementType... token) {
        advise(builder, token);
        return GeneratedParserUtilBase.consumeTokens(builder, pin, token);
    }

    private static void advise(PsiBuilder builder, IElementType... tokens) {
        if (builder instanceof GeneratedParserUtilBase.Builder) {
            builder = ((Builder) builder).getDelegate();
            if (builder instanceof RemapperPsiBuilder) {
                ((RemapperPsiBuilder) builder).expect(tokens);
            }
        }
    }

    private static class RemapperPsiBuilder extends PsiBuilderAdapter {
        private final Pattern identifier = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
        private final Pattern file = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_][a-zA-Z0-9_]*)*");
        private Queue<IElementType> expected = new LinkedList<>();

        public RemapperPsiBuilder(PsiBuilder builder) {
            super(builder);
        }

        @Nullable
        @Override
        public IElementType getTokenType() {
            IElementType source = super.getTokenType();
            String text = getTokenText();
            Pattern pattern = null;
            IElementType next = expected.poll();
            if (text != null) {
                if (next == KerboScriptTypes.IDENTIFIER) {
                    pattern = identifier;
                } else if (next == KerboScriptTypes.FILEIDENT) {
                    pattern = file;
                }
                if (pattern != null && pattern.matcher(text).matches()) {
                    remapCurrentToken(next);
                    return next;
                }
            }
            return source;
        }

        public void expect(IElementType... expected) {
            this.expected = new LinkedList<>(Arrays.asList(expected));
        }

    }

}
