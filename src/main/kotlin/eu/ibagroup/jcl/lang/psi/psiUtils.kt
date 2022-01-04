package eu.ibagroup.jcl.lang.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType

fun PsiElement.firstParentByElementType(elementType: IElementType): Pair<PsiElement?, Int> {
  var result: PsiElement? = this.parent
  var distance = 1
  while (result?.elementType != elementType) {
    result = result?.parent ?: break
    ++distance
  }
  return Pair(result, distance)
}
