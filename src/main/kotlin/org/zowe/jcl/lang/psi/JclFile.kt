/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.zowe.jcl.lang.JclFileType
import org.zowe.jcl.lang.JclLanguage

class JclFile(viewProvider: FileViewProvider): PsiFileBase(viewProvider, JclLanguage.INSTANCE) {
  override fun getFileType(): FileType = JclFileType.INSTANCE
  override fun toString(): String = "Jcl File"
}
