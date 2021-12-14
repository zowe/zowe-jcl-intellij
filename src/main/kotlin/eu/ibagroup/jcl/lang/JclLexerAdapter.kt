package eu.ibagroup.jcl.lang

import com.intellij.lexer.FlexAdapter

class JclLexerAdapter(): FlexAdapter(JclLexer(null)) {
  override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
    (flex as JclLexer).yycolumn = 0
    (flex as JclLexer).yyline = 0
    super.start(buffer, startOffset, endOffset, initialState)
  }
}
