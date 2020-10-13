package ksp.kos.ideaplugin.parsing

import com.intellij.testFramework.ParsingTestCase
import ksp.kos.ideaplugin.parser.KerboScriptParserDefinition

class KerboScriptParsingTest : ParsingTestCase("", "ks", KerboScriptParserDefinition()) {
    override fun getTestDataPath(): String = "src/test/testData/parsing"
    override fun includeRanges(): Boolean = true
    override fun skipSpaces(): Boolean = false

    fun testBasic() = doTest(true)
    fun testErrorRecovery() = doTest(true)
}
