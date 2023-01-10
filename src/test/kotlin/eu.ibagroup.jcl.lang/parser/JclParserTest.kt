package eu.ibagroup.jcl.lang.parser

import com.intellij.testFramework.ParsingTestCase
import eu.ibagroup.jcl.lang.JclParserDefinition

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