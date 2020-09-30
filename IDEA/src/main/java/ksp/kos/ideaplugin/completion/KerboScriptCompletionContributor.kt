package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import ksp.kos.ideaplugin.KerboScriptLanguage

class KerboScriptCompletionContributor : CompletionContributor() {
    init {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(KerboScriptLanguage.INSTANCE),
                KerboScriptKeywordProvider()
        )
    }

    override fun invokeAutoPopup(position: PsiElement, typeChar: Char): Boolean {
        // Disable autocompletion after the . that ends a statement.
        return typeChar != '.'
    }
}