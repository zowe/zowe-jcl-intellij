/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang.parser

import com.intellij.testFramework.ParsingTestCase
import org.zowe.jcl.lang.JclParserDefinition

class JclParserTest: ParsingTestCase("", "jcl", JclParserDefinition()) {

  /**
   * @return path to test data file directory relative to root of this module.
   */
  override fun getTestDataPath(): String = "src/test/resources/parser"

  override fun skipSpaces(): Boolean = false

  override fun includeRanges(): Boolean = true
  fun testParsingTestData() {
    doTest(true)
  }

  fun testIfConstruction() {
    doTest(true)
  }

  fun testNullStatement() {
    doTest(true)
  }

  fun testParams() {
    doTest(true)
  }

  fun testInstream() {
    doTest(true)
  }

  fun testOperatorFullName() {
    doTest(true)
  }

  fun testSequenceNumbers() {
    doTest(true)
  }

  fun testComment() {
    doTest(true)
  }

  fun testTuple() {
    doTest(true)
  }

  fun testString() {
    doTest(true)
  }

}
