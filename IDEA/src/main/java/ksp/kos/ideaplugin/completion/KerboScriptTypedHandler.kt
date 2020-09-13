package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import ksp.kos.ideaplugin.KerboScriptFileType

class KerboScriptTypedHandler : TypedHandlerDelegate() {
    /**
     * Disable autocompletion after a period. Otherwise ending a statement and going to the next line would be hell.
     */
    override fun checkAutoPopup(charTyped: Char, project: Project, editor: Editor, file: PsiFile): Result {
        return when {
            charTyped != '.' -> super.checkAutoPopup(charTyped, project, editor, file)
            file.fileType == KerboScriptFileType.INSTANCE -> Result.STOP
            else -> super.checkAutoPopup(charTyped, project, editor, file)
        }
    }
}