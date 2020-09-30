package ksp.kos.ideaplugin.refactoring

import ksp.kos.ideaplugin.KerboScriptPlatformTestBase
import org.intellij.lang.annotations.Language

class KerboScriptNormalizeTest : KerboScriptPlatformTestBase() {
    fun testNormalizeBasic() = doTest(
            """
                LOCAL <caret>x TO (a/b) + c + c + a*a + b/b.
            """,
            """
                LOCAL x TO (a + 2*c*b + a^2*b + b)/b.
            """
    )

    private fun doTest(
            @Language("KerboScript") before: String,
            @Language("KerboScript") after: String,
    ) {
        checkEditorAction(before, after, "ksp.kos.ideaplugin.actions.Normalize")
    }
}
