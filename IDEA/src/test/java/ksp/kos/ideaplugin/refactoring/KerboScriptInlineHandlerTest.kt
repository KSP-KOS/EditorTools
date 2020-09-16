package ksp.kos.ideaplugin.refactoring

import ksp.kos.ideaplugin.KerboScriptPlatformTestBase
import org.intellij.lang.annotations.Language

class KerboScriptInlineHandlerTest : KerboScriptPlatformTestBase() {
    // TODO - Remove blank lines when declaration is inlined
    fun testInlineVariableAtDeclaration() = doTest(
            """
                LOCAL <caret>b to a^2.
                LOCAL c to sqrt(b).
                LOCAL d to b - 1.
            """,
            """

                LOCAL c to sqrt((a^2)).
                LOCAL d to (a^2) - 1.
            """
    )

    fun testInlineVariableAtUsage() = doTest(
            """
                LOCAL b to a^2.
                LOCAL c to sqrt(<caret>b).
                LOCAL d to b - 1.
            """,
            """
                LOCAL b to a^2.
                LOCAL c to sqrt((a^2)).
                LOCAL d to b - 1.
            """
    )

    fun testInlineSimpleFunction() = doTest(
            """
                FUNCTION <caret>f {
                    PARAMETER x.
                    PARAMETER y.
                
                    RETURN x^2 + y.
                }
                
                LOCAL a TO f(b, c).
            """,
            """


                LOCAL a TO ((b)^2 + (c)).
            """
    )

    private fun doTest(
            @Language("KerboScript") before: String, @Language("KerboScript") after: String,
    ) {
        checkEditorAction(before, after, "Inline")
    }
}
