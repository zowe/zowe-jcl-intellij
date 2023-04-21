/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang

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
