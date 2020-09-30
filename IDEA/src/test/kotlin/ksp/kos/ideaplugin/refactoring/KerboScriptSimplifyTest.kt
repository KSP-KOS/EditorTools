package ksp.kos.ideaplugin.refactoring

import ksp.kos.ideaplugin.KerboScriptPlatformTestBase
import org.intellij.lang.annotations.Language

class KerboScriptSimplifyTest : KerboScriptPlatformTestBase() {
    fun testSimplifyBasic() = doTest(
            """
                LOCAL <caret>x TO (a/b) + c + c + a*a + b/b.
            """,
            """
                LOCAL x TO a/b + 2*c + a^2 + 1.
            """
    )

    private fun doTest(
            @Language("KerboScript") before: String,
            @Language("KerboScript") after: String,
    ) {
        checkEditorAction(before, after, "ksp.kos.ideaplugin.actions.Simplify")
    }
}
