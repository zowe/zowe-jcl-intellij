/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang.annotator

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class JclAnnotatorTest: BasePlatformTestCase() {

  /**
   * @return path to test data file directory relative to root of this module.
   */
  override fun getTestDataPath(): String = "src/test/resources/annotator"

  /**
   * Tests jsl file annotator using test name to get jsl file from resources.
   */
  private fun doTest() {
    myFixture.configureByFile(getTestName(false) + ".jcl")
    myFixture.testHighlighting(true, true, true)
  }
  fun testAnnotatorTestData() {
    doTest()
  }

  fun testOperator() {
    doTest()
  }

  fun testNoSuchOperator() {
    doTest()
  }

  fun testTuple() {
    doTest()
  }

  fun testUnclosedTuple() {
    doTest()
  }

  fun testIfCondition() {
    doTest()
  }

  fun testIncorrectIfCondition() {
    doTest()
  }

}
