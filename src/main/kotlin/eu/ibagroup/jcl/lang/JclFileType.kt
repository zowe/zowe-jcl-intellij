package eu.ibagroup.jcl.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class JclFileType: LanguageFileType(JclLanguage.INSTANCE) {

  companion object {
    public final val INSTANCE = JclFileType()
  }

  override fun getName(): String = "Jcl File"

  override fun getDescription(): String = "Job control language"

  override fun getDefaultExtension(): String = "jcl"

  override fun getIcon(): Icon = JclIcons.FILE
}
