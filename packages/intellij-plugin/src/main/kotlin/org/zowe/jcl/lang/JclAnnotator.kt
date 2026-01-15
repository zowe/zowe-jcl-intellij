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

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import org.zowe.jcl.lang.psi.JclTypes

class JclAnnotator: Annotator {
  override fun annotate(element: PsiElement, holder: AnnotationHolder) {
    val elementType = element.node.elementType
    if (elementType == JclTypes.OPERATOR && !JclConstants.OPERATORS.contains(element.text.uppercase())) {
      holder.newAnnotation(HighlightSeverity.WARNING, "No such operator").create()
    }
  }
}
