package eu.ibagroup.jcl.lang

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import eu.ibagroup.jcl.lang.psi.JclTypes

class JclAnnotator: Annotator {
  override fun annotate(element: PsiElement, holder: AnnotationHolder) {
    val elementType = element.node.elementType
    if (elementType == JclTypes.OPERATOR && !JclConstants.OPERATORS.contains(element.text.uppercase())) {
      holder.newAnnotation(HighlightSeverity.WARNING, "No such operator").create()
    }
  }
}
