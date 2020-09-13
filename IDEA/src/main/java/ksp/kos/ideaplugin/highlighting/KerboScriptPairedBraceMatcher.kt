package ksp.kos.ideaplugin.highlighting

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import ksp.kos.ideaplugin.psi.KerboScriptTypes

class KerboScriptPairedBraceMatcher : PairedBraceMatcher {
    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int = openingBraceOffset

    override fun getPairs(): Array<BracePair> = arrayOf(
            BracePair(KerboScriptTypes.CURLYOPEN, KerboScriptTypes.CURLYCLOSE, true)
    )

    override fun isPairedBracesAllowedBeforeType(leftBraceType: IElementType, contextType: IElementType?): Boolean = true
}