package eu.ibagroup.jcl.lang.psi

import com.intellij.psi.tree.IElementType
import eu.ibagroup.jcl.lang.JclLanguage

class JclElementType(debugName: String): IElementType(debugName, JclLanguage.INSTANCE)
