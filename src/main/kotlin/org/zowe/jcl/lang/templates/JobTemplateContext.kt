/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang.templates

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.util.elementType
import org.zowe.jcl.lang.JclLanguage
import org.zowe.jcl.lang.psi.JclTypes

class JobTemplateContext: TemplateContextType("JOB_CONTEXT", "Job context") {
  override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
    val elementType = templateActionContext.file.findElementAt(templateActionContext.startOffset).elementType
    return elementType?.language == JclLanguage.INSTANCE && elementType == JclTypes.OPERATOR_NAME
  }

}
