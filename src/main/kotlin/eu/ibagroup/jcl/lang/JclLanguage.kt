package eu.ibagroup.jcl.lang

import com.intellij.lang.Language

class JclLanguage: Language("JCL") {
  companion object {
    public final val INSTANCE = JclLanguage()
  }
}
