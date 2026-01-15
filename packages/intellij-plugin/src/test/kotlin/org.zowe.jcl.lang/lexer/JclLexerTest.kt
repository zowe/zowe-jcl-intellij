/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import org.zowe.jcl.lang.JclLexerAdapter

class JclLexerTest: LexerTestCase() {
  override fun createLexer(): Lexer = JclLexerAdapter()

  override fun getDirPath(): String = "src/test/resources/lexer/"

  override fun getPathToTestDataFile(extension: String?): String {
    return dirPath + getTestName(false) + extension
  }

  fun testLexerTestData() {
    doFileTest("jcl")
  }

}
