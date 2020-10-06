package ksp.kos.ideaplugin.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiElement
import ksp.kos.ideaplugin.psi.KerboScriptAtom
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement

class KerboScriptAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (!ApplicationManager.getApplication().isUnitTestMode) {
            // Until this is good enough, don't turn it on for general use; right now it's too noisy.
            return
        }
        if (element !is KerboScriptAtom) {
            return
        }

        if (element.name != null) {
            val declaration: KerboScriptNamedElement? = element.scope.cachedScope.findDeclaration(element)?.syntax

            if (declaration == null) {
                val message = "Unknown identifier `${element.name}`"
                if (ApplicationInfo.getInstance().apiVersion >= "201.3803.71") {
                    @Suppress("MissingRecentApi")
                    holder.newAnnotation(HighlightSeverity.ERROR, message).range(element).create()
                } else {
                    @Suppress("DEPRECATION")
                    holder.createErrorAnnotation(element, message)
                }
            }
        }
    }
}
