package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import ksp.kos.ideaplugin.Magic

class KerboScriptKeywordProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(completionParameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
        resultSet.addAllElements(Magic.keywords.flatMap {
            // Registering both an upper- and lower case lookup element to handle the case-insensivity.
            // We could add a single element and use withCaseSensivity(false), but that has the side effect that
            // when you type in lowercase, the completion shows uppercase (which can be somewhat unreadable when
            // you're not used to it).
            listOf(
                    LookupElementBuilder.create(it.toString()),
                    LookupElementBuilder.create(it.toString().toLowerCase())
            )
        })
    }
}