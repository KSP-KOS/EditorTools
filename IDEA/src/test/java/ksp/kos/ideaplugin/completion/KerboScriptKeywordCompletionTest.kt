package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionType
import ksp.kos.ideaplugin.KerboScriptFileType
import ksp.kos.ideaplugin.KerboScriptPlatformTestBase

class KerboScriptKeywordCompletionTest : KerboScriptPlatformTestBase() {
    fun `test completion of basic keyword lowercase`() {
        myFixture.configureByText(KerboScriptFileType.INSTANCE, "p<caret>")
        val result = myFixture.complete(CompletionType.BASIC)
        assert(result.any { it.lookupString == "print" } && result.none { it.lookupString == "PRINT" })
    }

    fun `test completion of basic keyword uppercase`() {
        myFixture.configureByText(KerboScriptFileType.INSTANCE, "P<caret>")
        val result = myFixture.complete(CompletionType.BASIC)
        assert(result.any { it.lookupString == "PRINT" } && result.none { it.lookupString == "print" })
    }

    fun `test no completion after period`() {
        myFixture.configureByText(KerboScriptFileType.INSTANCE, "print x.<caret>")
        val result = myFixture.complete(CompletionType.BASIC)
        assert(result.isEmpty())
    }

//    fun `test logging`() {
//        val s = "abc"
//    }
}