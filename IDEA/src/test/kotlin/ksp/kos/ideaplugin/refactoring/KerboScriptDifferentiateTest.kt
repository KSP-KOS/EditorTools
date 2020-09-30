package ksp.kos.ideaplugin.refactoring

import ksp.kos.ideaplugin.KerboScriptPlatformTestBase
import org.intellij.lang.annotations.Language

class KerboScriptDifferentiateTest : KerboScriptPlatformTestBase() {
    // TODO - Handle casing preferences
    fun testDifferentiateVariable() = doTest(
            """
                LOCAL <caret>y TO x^2.
            """,
            """
                LOCAL y TO x^2.
                local y_ to 2*x_*x.
            """
    )

    fun testDifferentiateFunction() = doTest(
            """
                FUNCTION <caret>f {
                    PARAMETER x.
                    PARAMETER y.
                
                    RETURN x^2 + y.
                }
            """,
            """
                @lazyglobal off.
                
                function f_ {
                    parameter x.
                    parameter x_.
                    parameter y_.
                
                    return 2*x_*x + y_.
                }
            """,
            afterFilePath = "main_.ks"
    )

    private fun doTest(
            @Language("KerboScript") before: String,
            @Language("KerboScript") after: String,
            afterFilePath: String? = null,
    ) {
        checkEditorAction(
                before,
                after,
                "ksp.kos.ideaplugin.actions.differentiate.Differentiate",
                afterFilePath = afterFilePath,
        )
    }
}
