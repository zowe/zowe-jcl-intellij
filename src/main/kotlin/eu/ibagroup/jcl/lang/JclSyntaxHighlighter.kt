package eu.ibagroup.jcl.lang

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType
import eu.ibagroup.jcl.lang.psi.JclTypes

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey


class JclSyntaxHighlighter: SyntaxHighlighter {

  companion object {

    public final val COMMENT =
      createTextAttributesKey("JCL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)

    public final val OPERATOR =
      createTextAttributesKey("JCL_PARAM", DefaultLanguageHighlighterColors.KEYWORD)

    public final val LINE_START =
      createTextAttributesKey("JCL_LINE_START", DefaultLanguageHighlighterColors.OPERATION_SIGN)

    public final val OPERATOR_NAME =
      createTextAttributesKey("JCL_OPERATOR_NAME", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)

    public final val STRING =
      createTextAttributesKey("JCL_STRING", DefaultLanguageHighlighterColors.STRING)

    public final val PARAM_NAME =
      createTextAttributesKey("JCL_PARAM_NAME", DefaultLanguageHighlighterColors.CONSTANT)

    public final val PARAM_VALUE =
      createTextAttributesKey("JCL_PARAM_VALUE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)

    public final val INSTREAM_TEXT =
      createTextAttributesKey("JCL_INSTREAM_TEXT", DefaultLanguageHighlighterColors.STRING)

    public final val SEQUENCE_NUMBERS =
      createTextAttributesKey("JCL_SEQUENCE_NUMBERS", DefaultLanguageHighlighterColors.METADATA)

    private final val COMMENT_KEYS = arrayOf(COMMENT)
    private final val OPERATOR_KEYS = arrayOf(OPERATOR)
    private final val LINE_START_KEYS = arrayOf(LINE_START)
    private final val OPERATOR_NAME_KEYS = arrayOf(OPERATOR_NAME)
    private final val STRING_KEYS = arrayOf(STRING)
    private final val PARAM_NAME_KEYS = arrayOf(PARAM_NAME)
    private final val PARAM_VALUE_KEYS = arrayOf(PARAM_VALUE)
//    private final val SEQUENCE_NUMBERS_KEYS = arrayOf(SEQUENCE_NUMBERS)
    private final val INSTREAM_TEXT_KEYS = arrayOf(INSTREAM_TEXT)
    private final val EMPTY_KEYS = arrayOf<TextAttributesKey>()
  }

  override fun getHighlightingLexer(): Lexer = JclLexerAdapter()

  override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
    if (tokenType == JclTypes.COMMENT) {
      return COMMENT_KEYS
    }
    if (tokenType == JclTypes.OPERATOR) {
      return OPERATOR_KEYS
    }
    if (tokenType == JclTypes.LINE_START) {
      return LINE_START_KEYS
    }
    if (tokenType == JclTypes.OPERATOR_NAME) {
      return OPERATOR_NAME_KEYS
    }
    if (tokenType == JclTypes.STRING) {
      return STRING_KEYS
    }
    if (tokenType == JclTypes.PARAM_KEY || tokenType == JclTypes.INSTREAM_START || tokenType == JclTypes.INSTREAM_END) {
      return PARAM_NAME_KEYS
    }
    if (tokenType == JclTypes.SIMPLE_VALUE) {
      return PARAM_VALUE_KEYS
    }
    if (tokenType == JclTypes.TUPLE) {
      return PARAM_VALUE_KEYS
    }
    if (tokenType == JclTypes.SEQUENCE_NUMBERS) {
      return COMMENT_KEYS
    }
    if (tokenType == JclTypes.INSTREAM_TEXT) {
      return INSTREAM_TEXT_KEYS
    }
    return EMPTY_KEYS
  }
}
