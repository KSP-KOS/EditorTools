package ksp.kos.ideaplugin.actions

import com.intellij.icons.AllIcons
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.util.Consumer
import ksp.kos.ideaplugin.KerboScriptFileType

class NewKerboScriptFileAction : CreateElementActionBase("KerboScript File", "Create new KerboScript file", AllIcons.FileTypes.Text) {
    override fun create(s: String, psiDirectory: PsiDirectory): Array<PsiElement> = arrayOf()

    override fun getActionName(p0: PsiDirectory?, p1: String?): String = ""

    override fun getErrorTitle(): String = "Error"

    override fun invokeDialog(project: Project, directory: PsiDirectory, elementsConsumer: java.util.function.Consumer<Array<PsiElement>>) {
        val builder = CreateFileFromTemplateDialog.createDialog(project)
        builder.apply {
            setTitle("Create a new KerboScript file")
            addKind("KerboScript", AllIcons.FileTypes.Text, "ks")
        }
        val consumer: Consumer<in PsiElement?> = Consumer {  }
        builder.show("", null, KerboScriptFileCreator(project, directory), consumer)
    }

    inner class KerboScriptFileCreator(val project: Project, val directory: PsiDirectory) : CreateFileFromTemplateDialog.FileCreator<PsiElement?> {
        private fun getFileName(fileName: String): String {
            val extension = ".${KerboScriptFileType.INSTANCE.defaultExtension}"
            return if (fileName.endsWith(extension)) fileName else fileName + extension
        }

        override fun createFile(fileName: String, option: String): PsiElement? {
            val fileFactory = PsiFileFactory.getInstance(project)
            val file = fileFactory.createFileFromText(getFileName(fileName), KerboScriptFileType.INSTANCE, "")
            val psiFile = directory.add(file) as PsiFile
            // Open the file we just created.
            FileEditorManager.getInstance(project).openFile(psiFile.virtualFile, true)
            return psiFile
        }

        override fun startInWriteAction(): Boolean = true

        override fun getActionName(p0: String, p1: String): String = "New KerboScript file"
    }
}