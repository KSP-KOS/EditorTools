package ksp.kos.ideaplugin

class KerboScriptFindUsagesProviderTest : KerboScriptPlatformTestBase() {
    override val subdir: String = "findUsages"

    fun testBasic() {
        val usages = myFixture.testFindUsagesUsingAction("Basic.ks")
        val expected = setOf(
            "    PRINT param.",
            "    PRINT param().",
            "    LOCAL var TO param.",
            "    LOCAL var TO param().",
        )
        assertEquals(expected, usages.map { it.presentation.plainText }.toSet())
    }
}
