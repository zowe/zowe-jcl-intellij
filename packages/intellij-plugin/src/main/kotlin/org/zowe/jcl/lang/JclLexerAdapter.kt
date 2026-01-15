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

import com.intellij.lexer.FlexAdapter

class JclLexerAdapter(): FlexAdapter(JclLexer(null)) {
  override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
    (flex as JclLexer).prevState = 0
    (flex as JclLexer).tupleInnerCounter = 0
    (flex as JclLexer).movedBack = false
    (flex as JclLexer).instreamParamStarted = false
    (flex as JclLexer).firstParamInitialized = false
    super.start(buffer, startOffset, endOffset, initialState)
  }
}
