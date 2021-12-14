package eu.ibagroup.jcl.lang.psi

import com.intellij.psi.tree.IElementType
import eu.ibagroup.jcl.lang.JclLanguage
import java.util.*

class JclTokenType(debugName: String): IElementType(debugName, JclLanguage.INSTANCE) {
  override fun toString(): String = super.toString().lowercase(Locale.getDefault())
}
