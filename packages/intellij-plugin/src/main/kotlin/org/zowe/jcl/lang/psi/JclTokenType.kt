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

import com.intellij.psi.tree.IElementType
import org.zowe.jcl.lang.JclLanguage
import java.util.*

class JclTokenType(debugName: String): IElementType(debugName, JclLanguage.INSTANCE) {
  override fun toString(): String = super.toString().lowercase(Locale.getDefault())
}
