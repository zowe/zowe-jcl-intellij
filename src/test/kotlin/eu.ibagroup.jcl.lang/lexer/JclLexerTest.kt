package eu.ibagroup.jcl.lang.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import eu.ibagroup.jcl.lang.JclLexerAdapter

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