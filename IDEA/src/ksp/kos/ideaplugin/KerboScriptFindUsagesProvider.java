package ksp.kos.ideaplugin;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import ksp.kos.ideaplugin.parser.KerboScriptLexerAdapter;
import ksp.kos.ideaplugin.parser.KerboScriptParserDefinition;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import ksp.kos.ideaplugin.psi.impl.KerboScriptNamedElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 04/01/16.
 *
 * @author ptasha
 */
public class KerboScriptFindUsagesProvider implements FindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new KerboScriptLexerAdapter(),
                TokenSet.create(KerboScriptTypes.IDENTIFIER, KerboScriptTypes.FILEIDENT),
                KerboScriptParserDefinition.COMMENTS, TokenSet.create(KerboScriptTypes.STRING));
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof KerboScriptNamedElementImpl) {
            KerboScriptNamedElementImpl kerboScriptElement = (KerboScriptNamedElementImpl) element;
            if (kerboScriptElement.isFunction()) {
                return "function";
            } else if (kerboScriptElement.isVariable()) {
                return "variable";
            } else if (kerboScriptElement.isFile()) {
                return "script";
            }
        }
        return "";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiNamedElement) {
            String name = ((PsiNamedElement) element).getName();
            if (name!=null) {
                return name;
            }
        }
        return "";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return getDescriptiveName(element);
    }}
