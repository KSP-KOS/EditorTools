package ksp.kos.ideaplugin

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language

abstract class KerboScriptPlatformTestBase : BasePlatformTestCase() {
    open val subdir = ""

    override fun getTestDataPath(): String = "src/test/testData/$subdir"

    protected open fun checkEditorAction(
            @Language("KerboScript") before: String,
            @Language("KerboScript") after: String,
            actionId: String,
            trimIndent: Boolean = true,
            afterFilePath: String? = null,
    ) {
        fun String.trimIndentIfNeeded(): String = if (trimIndent) trimIndent() else this

        checkByText(before.trimIndentIfNeeded(), after.trimIndentIfNeeded(), afterFilePath) {
            myFixture.performEditorAction(actionId)
        }
    }

    protected fun checkByText(
            @Language("KerboScript") before: String,
            @Language("KerboScript") after: String,
            afterFilePath: String? = null,
            action: () -> Unit,
    ) {
        InlineFile(before)
        action()
        if (afterFilePath != null) {
            myFixture.checkResult(afterFilePath, replaceCaretMarker(after), false)
        } else {
            myFixture.checkResult(replaceCaretMarker(after), false)
        }
    }

    @Suppress("TestFunctionName")
    protected fun InlineFile(@Language("KerboScript") code: String, name: String = "main.ks"): InlineFile {
        return InlineFile(myFixture, code, name)
    }
}
