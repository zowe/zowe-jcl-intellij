package eu.ibagroup.jcl.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import eu.ibagroup.jcl.lang.JclFileType
import eu.ibagroup.jcl.lang.JclLanguage

class JclFile(viewProvider: FileViewProvider): PsiFileBase(viewProvider, JclLanguage.INSTANCE) {
  override fun getFileType(): FileType = JclFileType.INSTANCE
  override fun toString(): String = "Jcl File"
}
