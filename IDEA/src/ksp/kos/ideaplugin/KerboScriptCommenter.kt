package ksp.kos.ideaplugin

import com.intellij.lang.Commenter

open class KerboScriptCommenter : Commenter {
    override fun getLineCommentPrefix(): String? = "//"

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null

    override fun getBlockCommentPrefix(): String? = null

    override fun getBlockCommentSuffix(): String? = null

}