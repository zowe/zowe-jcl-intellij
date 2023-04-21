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
