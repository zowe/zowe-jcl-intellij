// This is a generated file. Not intended for manual editing.
package org.zowe.jcl.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.zowe.jcl.lang.psi.impl.*;

public interface JclTypes {

  IElementType END_IF_CONSTR = new JclElementType("END_IF_CONSTR");
  IElementType IF_BODY = new JclElementType("IF_BODY");
  IElementType IF_CONDITION = new JclElementType("IF_CONDITION");
  IElementType IF_CONSTR = new JclElementType("IF_CONSTR");
  IElementType IF_VALUE = new JclElementType("IF_VALUE");
  IElementType INSTREAM = new JclElementType("INSTREAM");
  IElementType INSTREAM_CONTENT = new JclElementType("INSTREAM_CONTENT");
  IElementType INSTREAM_LINE = new JclElementType("INSTREAM_LINE");
  IElementType JCL_LINE = new JclElementType("JCL_LINE");
  IElementType KEY_VALUE_PARAM = new JclElementType("KEY_VALUE_PARAM");
  IElementType LINE_CONTINUATION = new JclElementType("LINE_CONTINUATION");
  IElementType NULL_STATEMENT = new JclElementType("NULL_STATEMENT");
  IElementType OPERATOR_FULL_NAME = new JclElementType("OPERATOR_FULL_NAME");
  IElementType PARAM = new JclElementType("PARAM");
  IElementType PARAMS = new JclElementType("PARAMS");
  IElementType PARAM_VALUE = new JclElementType("PARAM_VALUE");
  IElementType STRING = new JclElementType("STRING");
  IElementType STRING_INNER_CONTENT = new JclElementType("STRING_INNER_CONTENT");
  IElementType TUPLE = new JclElementType("TUPLE");
  IElementType TUPLE_INNER_CONTENT = new JclElementType("TUPLE_INNER_CONTENT");
  IElementType TUPLE_PARAM = new JclElementType("TUPLE_PARAM");

  IElementType COMMENT = new JclTokenType("COMMENT");
  IElementType CRLF = new JclTokenType("CRLF");
  IElementType DOT = new JclTokenType("DOT");
  IElementType END_IF = new JclTokenType("END_IF");
  IElementType IF_CONDITION_END = new JclTokenType("IF_CONDITION_END");
  IElementType IF_CONDITION_OPERATOR = new JclTokenType("IF_CONDITION_OPERATOR");
  IElementType IF_CONDITION_START = new JclTokenType("IF_CONDITION_START");
  IElementType IF_OPERATOR = new JclTokenType("IF_OPERATOR");
  IElementType IF_OPERATOR_NOT = new JclTokenType("IF_OPERATOR_NOT");
  IElementType INSTREAM_END = new JclTokenType("INSTREAM_END");
  IElementType INSTREAM_START = new JclTokenType("INSTREAM_START");
  IElementType INSTREAM_TEXT = new JclTokenType("INSTREAM_TEXT");
  IElementType LINE_START = new JclTokenType("LINE_START");
  IElementType OPERATOR = new JclTokenType("OPERATOR");
  IElementType OPERATOR_NAME = new JclTokenType("OPERATOR_NAME");
  IElementType OPERATOR_OVERRIDE_NAME = new JclTokenType("OPERATOR_OVERRIDE_NAME");
  IElementType PARAM_DELIM = new JclTokenType("PARAM_DELIM");
  IElementType PARAM_EQUALS = new JclTokenType("PARAM_EQUALS");
  IElementType PARAM_KEY = new JclTokenType("PARAM_KEY");
  IElementType PROPERTY_NAME = new JclTokenType("PROPERTY_NAME");
  IElementType SEQUENCE_NUMBERS = new JclTokenType("SEQUENCE_NUMBERS");
  IElementType SIMPLE_VALUE = new JclTokenType("SIMPLE_VALUE");
  IElementType STRING_BRACKET = new JclTokenType("STRING_BRACKET");
  IElementType STRING_CONTENT = new JclTokenType("STRING_CONTENT");
  IElementType TEMPLATE_PARAM = new JclTokenType("TEMPLATE_PARAM");
  IElementType THEN_OPERATOR = new JclTokenType("THEN_OPERATOR");
  IElementType TUPLE_END = new JclTokenType("TUPLE_END");
  IElementType TUPLE_PARAM_DELIM = new JclTokenType("TUPLE_PARAM_DELIM");
  IElementType TUPLE_START = new JclTokenType("TUPLE_START");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == END_IF_CONSTR) {
        return new JclEndIfConstrImpl(node);
      }
      else if (type == IF_BODY) {
        return new JclIfBodyImpl(node);
      }
      else if (type == IF_CONDITION) {
        return new JclIfConditionImpl(node);
      }
      else if (type == IF_CONSTR) {
        return new JclIfConstrImpl(node);
      }
      else if (type == IF_VALUE) {
        return new JclIfValueImpl(node);
      }
      else if (type == INSTREAM) {
        return new JclInstreamImpl(node);
      }
      else if (type == INSTREAM_CONTENT) {
        return new JclInstreamContentImpl(node);
      }
      else if (type == INSTREAM_LINE) {
        return new JclInstreamLineImpl(node);
      }
      else if (type == JCL_LINE) {
        return new JclJclLineImpl(node);
      }
      else if (type == KEY_VALUE_PARAM) {
        return new JclKeyValueParamImpl(node);
      }
      else if (type == LINE_CONTINUATION) {
        return new JclLineContinuationImpl(node);
      }
      else if (type == NULL_STATEMENT) {
        return new JclNullStatementImpl(node);
      }
      else if (type == OPERATOR_FULL_NAME) {
        return new JclOperatorFullNameImpl(node);
      }
      else if (type == PARAM) {
        return new JclParamImpl(node);
      }
      else if (type == PARAMS) {
        return new JclParamsImpl(node);
      }
      else if (type == PARAM_VALUE) {
        return new JclParamValueImpl(node);
      }
      else if (type == STRING) {
        return new JclStringImpl(node);
      }
      else if (type == STRING_INNER_CONTENT) {
        return new JclStringInnerContentImpl(node);
      }
      else if (type == TUPLE) {
        return new JclTupleImpl(node);
      }
      else if (type == TUPLE_INNER_CONTENT) {
        return new JclTupleInnerContentImpl(node);
      }
      else if (type == TUPLE_PARAM) {
        return new JclTupleParamImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
