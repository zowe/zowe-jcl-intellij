// This is a generated file. Not intended for manual editing.
package org.zowe.jcl.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.zowe.jcl.lang.psi.JclTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class JclParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return jclFile(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // LINE_START OPERATOR_FULL_NAME? END_IF COMMENT? SEQUENCE_NUMBERS?
  public static boolean END_IF_CONSTR(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "END_IF_CONSTR")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && END_IF_CONSTR_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END_IF);
    result_ = result_ && END_IF_CONSTR_3(builder_, level_ + 1);
    result_ = result_ && END_IF_CONSTR_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, END_IF_CONSTR, result_);
    return result_;
  }

  // OPERATOR_FULL_NAME?
  private static boolean END_IF_CONSTR_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "END_IF_CONSTR_1")) return false;
    OPERATOR_FULL_NAME(builder_, level_ + 1);
    return true;
  }

  // COMMENT?
  private static boolean END_IF_CONSTR_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "END_IF_CONSTR_3")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean END_IF_CONSTR_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "END_IF_CONSTR_4")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // IF_OPERATOR IF_CONDITION THEN_OPERATOR COMMENT? SEQUENCE_NUMBERS?
  public static boolean IF_BODY(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_BODY")) return false;
    if (!nextTokenIs(builder_, IF_OPERATOR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF_OPERATOR);
    result_ = result_ && IF_CONDITION(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, THEN_OPERATOR);
    result_ = result_ && IF_BODY_3(builder_, level_ + 1);
    result_ = result_ && IF_BODY_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, IF_BODY, result_);
    return result_;
  }

  // COMMENT?
  private static boolean IF_BODY_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_BODY_3")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean IF_BODY_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_BODY_4")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // IF_VALUE (IF_CONDITION_OPERATOR LINE_START? IF_VALUE)*
  public static boolean IF_CONDITION(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONDITION")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, IF_CONDITION, "<if condition>");
    result_ = IF_VALUE(builder_, level_ + 1);
    result_ = result_ && IF_CONDITION_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (IF_CONDITION_OPERATOR LINE_START? IF_VALUE)*
  private static boolean IF_CONDITION_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONDITION_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!IF_CONDITION_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "IF_CONDITION_1", pos_)) break;
    }
    return true;
  }

  // IF_CONDITION_OPERATOR LINE_START? IF_VALUE
  private static boolean IF_CONDITION_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONDITION_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF_CONDITION_OPERATOR);
    result_ = result_ && IF_CONDITION_1_0_1(builder_, level_ + 1);
    result_ = result_ && IF_VALUE(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LINE_START?
  private static boolean IF_CONDITION_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONDITION_1_0_1")) return false;
    consumeToken(builder_, LINE_START);
    return true;
  }

  /* ********************************************************** */
  // LINE_START OPERATOR_FULL_NAME? IF_BODY JCL_LINE* END_IF_CONSTR
  public static boolean IF_CONSTR(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONSTR")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && IF_CONSTR_1(builder_, level_ + 1);
    result_ = result_ && IF_BODY(builder_, level_ + 1);
    result_ = result_ && IF_CONSTR_3(builder_, level_ + 1);
    result_ = result_ && END_IF_CONSTR(builder_, level_ + 1);
    exit_section_(builder_, marker_, IF_CONSTR, result_);
    return result_;
  }

  // OPERATOR_FULL_NAME?
  private static boolean IF_CONSTR_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONSTR_1")) return false;
    OPERATOR_FULL_NAME(builder_, level_ + 1);
    return true;
  }

  // JCL_LINE*
  private static boolean IF_CONSTR_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_CONSTR_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!JCL_LINE(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "IF_CONSTR_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IF_OPERATOR_NOT? ((PROPERTY_NAME (DOT PROPERTY_NAME)*) | (IF_CONDITION_START IF_CONDITION IF_CONDITION_END))
  public static boolean IF_VALUE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, IF_VALUE, "<if value>");
    result_ = IF_VALUE_0(builder_, level_ + 1);
    result_ = result_ && IF_VALUE_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // IF_OPERATOR_NOT?
  private static boolean IF_VALUE_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_0")) return false;
    consumeToken(builder_, IF_OPERATOR_NOT);
    return true;
  }

  // (PROPERTY_NAME (DOT PROPERTY_NAME)*) | (IF_CONDITION_START IF_CONDITION IF_CONDITION_END)
  private static boolean IF_VALUE_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = IF_VALUE_1_0(builder_, level_ + 1);
    if (!result_) result_ = IF_VALUE_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PROPERTY_NAME (DOT PROPERTY_NAME)*
  private static boolean IF_VALUE_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, PROPERTY_NAME);
    result_ = result_ && IF_VALUE_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (DOT PROPERTY_NAME)*
  private static boolean IF_VALUE_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_1_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!IF_VALUE_1_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "IF_VALUE_1_0_1", pos_)) break;
    }
    return true;
  }

  // DOT PROPERTY_NAME
  private static boolean IF_VALUE_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, PROPERTY_NAME);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IF_CONDITION_START IF_CONDITION IF_CONDITION_END
  private static boolean IF_VALUE_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IF_VALUE_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF_CONDITION_START);
    result_ = result_ && IF_CONDITION(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, IF_CONDITION_END);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // INSTREAM_CONTENT (INSTREAM_END SEQUENCE_NUMBERS?)?
  public static boolean INSTREAM(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INSTREAM, "<instream>");
    result_ = INSTREAM_CONTENT(builder_, level_ + 1);
    result_ = result_ && INSTREAM_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (INSTREAM_END SEQUENCE_NUMBERS?)?
  private static boolean INSTREAM_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_1")) return false;
    INSTREAM_1_0(builder_, level_ + 1);
    return true;
  }

  // INSTREAM_END SEQUENCE_NUMBERS?
  private static boolean INSTREAM_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, INSTREAM_END);
    result_ = result_ && INSTREAM_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // SEQUENCE_NUMBERS?
  private static boolean INSTREAM_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_1_0_1")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // (INSTREAM_LINE | ((INSTREAM_TEXT | COMMENT) SEQUENCE_NUMBERS?))*
  public static boolean INSTREAM_CONTENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_CONTENT")) return false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INSTREAM_CONTENT, "<instream content>");
    while (true) {
      int pos_ = current_position_(builder_);
      if (!INSTREAM_CONTENT_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "INSTREAM_CONTENT", pos_)) break;
    }
    exit_section_(builder_, level_, marker_, true, false, null);
    return true;
  }

  // INSTREAM_LINE | ((INSTREAM_TEXT | COMMENT) SEQUENCE_NUMBERS?)
  private static boolean INSTREAM_CONTENT_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_CONTENT_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = INSTREAM_LINE(builder_, level_ + 1);
    if (!result_) result_ = INSTREAM_CONTENT_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (INSTREAM_TEXT | COMMENT) SEQUENCE_NUMBERS?
  private static boolean INSTREAM_CONTENT_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_CONTENT_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = INSTREAM_CONTENT_0_1_0(builder_, level_ + 1);
    result_ = result_ && INSTREAM_CONTENT_0_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // INSTREAM_TEXT | COMMENT
  private static boolean INSTREAM_CONTENT_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_CONTENT_0_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INSTREAM_TEXT);
    if (!result_) result_ = consumeToken(builder_, COMMENT);
    return result_;
  }

  // SEQUENCE_NUMBERS?
  private static boolean INSTREAM_CONTENT_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_CONTENT_0_1_1")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // (LINE_START OPERATOR PARAMS?) | LINE_CONTINUATION
  public static boolean INSTREAM_LINE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_LINE")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = INSTREAM_LINE_0(builder_, level_ + 1);
    if (!result_) result_ = LINE_CONTINUATION(builder_, level_ + 1);
    exit_section_(builder_, marker_, INSTREAM_LINE, result_);
    return result_;
  }

  // LINE_START OPERATOR PARAMS?
  private static boolean INSTREAM_LINE_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_LINE_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LINE_START, OPERATOR);
    result_ = result_ && INSTREAM_LINE_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PARAMS?
  private static boolean INSTREAM_LINE_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "INSTREAM_LINE_0_2")) return false;
    PARAMS(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (LINE_START OPERATOR_FULL_NAME? OPERATOR PARAMS INSTREAM? COMMENT? SEQUENCE_NUMBERS?) | (LINE_START OPERATOR_FULL_NAME? OPERATOR COMMENT? SEQUENCE_NUMBERS?) | LINE_CONTINUATION | IF_CONSTR
  public static boolean JCL_LINE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = JCL_LINE_0(builder_, level_ + 1);
    if (!result_) result_ = JCL_LINE_1(builder_, level_ + 1);
    if (!result_) result_ = LINE_CONTINUATION(builder_, level_ + 1);
    if (!result_) result_ = IF_CONSTR(builder_, level_ + 1);
    exit_section_(builder_, marker_, JCL_LINE, result_);
    return result_;
  }

  // LINE_START OPERATOR_FULL_NAME? OPERATOR PARAMS INSTREAM? COMMENT? SEQUENCE_NUMBERS?
  private static boolean JCL_LINE_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && JCL_LINE_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, OPERATOR);
    result_ = result_ && PARAMS(builder_, level_ + 1);
    result_ = result_ && JCL_LINE_0_4(builder_, level_ + 1);
    result_ = result_ && JCL_LINE_0_5(builder_, level_ + 1);
    result_ = result_ && JCL_LINE_0_6(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // OPERATOR_FULL_NAME?
  private static boolean JCL_LINE_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_0_1")) return false;
    OPERATOR_FULL_NAME(builder_, level_ + 1);
    return true;
  }

  // INSTREAM?
  private static boolean JCL_LINE_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_0_4")) return false;
    INSTREAM(builder_, level_ + 1);
    return true;
  }

  // COMMENT?
  private static boolean JCL_LINE_0_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_0_5")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean JCL_LINE_0_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_0_6")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  // LINE_START OPERATOR_FULL_NAME? OPERATOR COMMENT? SEQUENCE_NUMBERS?
  private static boolean JCL_LINE_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && JCL_LINE_1_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, OPERATOR);
    result_ = result_ && JCL_LINE_1_3(builder_, level_ + 1);
    result_ = result_ && JCL_LINE_1_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // OPERATOR_FULL_NAME?
  private static boolean JCL_LINE_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_1_1")) return false;
    OPERATOR_FULL_NAME(builder_, level_ + 1);
    return true;
  }

  // COMMENT?
  private static boolean JCL_LINE_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_1_3")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean JCL_LINE_1_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_LINE_1_4")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // JCL_LINE|NULL_STATEMENT|COMMENT|CRLF
  static boolean JCL_SYNTAX(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "JCL_SYNTAX")) return false;
    boolean result_;
    result_ = JCL_LINE(builder_, level_ + 1);
    if (!result_) result_ = NULL_STATEMENT(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, COMMENT);
    if (!result_) result_ = consumeToken(builder_, CRLF);
    return result_;
  }

  /* ********************************************************** */
  // PARAM_KEY (PARAM_EQUALS PARAM_VALUE?)*
  public static boolean KEY_VALUE_PARAM(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "KEY_VALUE_PARAM")) return false;
    if (!nextTokenIs(builder_, PARAM_KEY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, PARAM_KEY);
    result_ = result_ && KEY_VALUE_PARAM_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, KEY_VALUE_PARAM, result_);
    return result_;
  }

  // (PARAM_EQUALS PARAM_VALUE?)*
  private static boolean KEY_VALUE_PARAM_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "KEY_VALUE_PARAM_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!KEY_VALUE_PARAM_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "KEY_VALUE_PARAM_1", pos_)) break;
    }
    return true;
  }

  // PARAM_EQUALS PARAM_VALUE?
  private static boolean KEY_VALUE_PARAM_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "KEY_VALUE_PARAM_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, PARAM_EQUALS);
    result_ = result_ && KEY_VALUE_PARAM_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PARAM_VALUE?
  private static boolean KEY_VALUE_PARAM_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "KEY_VALUE_PARAM_1_0_1")) return false;
    PARAM_VALUE(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // LINE_START PARAMS COMMENT? SEQUENCE_NUMBERS?
  public static boolean LINE_CONTINUATION(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "LINE_CONTINUATION")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && PARAMS(builder_, level_ + 1);
    result_ = result_ && LINE_CONTINUATION_2(builder_, level_ + 1);
    result_ = result_ && LINE_CONTINUATION_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, LINE_CONTINUATION, result_);
    return result_;
  }

  // COMMENT?
  private static boolean LINE_CONTINUATION_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "LINE_CONTINUATION_2")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean LINE_CONTINUATION_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "LINE_CONTINUATION_3")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // LINE_START SEQUENCE_NUMBERS?
  public static boolean NULL_STATEMENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "NULL_STATEMENT")) return false;
    if (!nextTokenIs(builder_, LINE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LINE_START);
    result_ = result_ && NULL_STATEMENT_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, NULL_STATEMENT, result_);
    return result_;
  }

  // SEQUENCE_NUMBERS?
  private static boolean NULL_STATEMENT_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "NULL_STATEMENT_1")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // OPERATOR_NAME (DOT OPERATOR_OVERRIDE_NAME)?
  public static boolean OPERATOR_FULL_NAME(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "OPERATOR_FULL_NAME")) return false;
    if (!nextTokenIs(builder_, OPERATOR_NAME)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, OPERATOR_NAME);
    result_ = result_ && OPERATOR_FULL_NAME_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, OPERATOR_FULL_NAME, result_);
    return result_;
  }

  // (DOT OPERATOR_OVERRIDE_NAME)?
  private static boolean OPERATOR_FULL_NAME_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "OPERATOR_FULL_NAME_1")) return false;
    OPERATOR_FULL_NAME_1_0(builder_, level_ + 1);
    return true;
  }

  // DOT OPERATOR_OVERRIDE_NAME
  private static boolean OPERATOR_FULL_NAME_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "OPERATOR_FULL_NAME_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, OPERATOR_OVERRIDE_NAME);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KEY_VALUE_PARAM | TEMPLATE_PARAM | STRING | TUPLE | PARAM_KEY | INSTREAM_START
  public static boolean PARAM(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAM")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PARAM, "<param>");
    result_ = KEY_VALUE_PARAM(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, TEMPLATE_PARAM);
    if (!result_) result_ = STRING(builder_, level_ + 1);
    if (!result_) result_ = TUPLE(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, PARAM_KEY);
    if (!result_) result_ = consumeToken(builder_, INSTREAM_START);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // (PARAM? PARAM_DELIM)* ((COMMENT? SEQUENCE_NUMBERS? LINE_CONTINUATION) | (PARAM COMMENT? SEQUENCE_NUMBERS?))
  public static boolean PARAMS(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PARAMS, "<params>");
    result_ = PARAMS_0(builder_, level_ + 1);
    result_ = result_ && PARAMS_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (PARAM? PARAM_DELIM)*
  private static boolean PARAMS_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!PARAMS_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "PARAMS_0", pos_)) break;
    }
    return true;
  }

  // PARAM? PARAM_DELIM
  private static boolean PARAMS_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = PARAMS_0_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, PARAM_DELIM);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PARAM?
  private static boolean PARAMS_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_0_0_0")) return false;
    PARAM(builder_, level_ + 1);
    return true;
  }

  // (COMMENT? SEQUENCE_NUMBERS? LINE_CONTINUATION) | (PARAM COMMENT? SEQUENCE_NUMBERS?)
  private static boolean PARAMS_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = PARAMS_1_0(builder_, level_ + 1);
    if (!result_) result_ = PARAMS_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // COMMENT? SEQUENCE_NUMBERS? LINE_CONTINUATION
  private static boolean PARAMS_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = PARAMS_1_0_0(builder_, level_ + 1);
    result_ = result_ && PARAMS_1_0_1(builder_, level_ + 1);
    result_ = result_ && LINE_CONTINUATION(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // COMMENT?
  private static boolean PARAMS_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_0_0")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean PARAMS_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_0_1")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  // PARAM COMMENT? SEQUENCE_NUMBERS?
  private static boolean PARAMS_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = PARAM(builder_, level_ + 1);
    result_ = result_ && PARAMS_1_1_1(builder_, level_ + 1);
    result_ = result_ && PARAMS_1_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // COMMENT?
  private static boolean PARAMS_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_1_1")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean PARAMS_1_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAMS_1_1_2")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // STRING | TUPLE | SIMPLE_VALUE
  public static boolean PARAM_VALUE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PARAM_VALUE")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PARAM_VALUE, "<param value>");
    result_ = STRING(builder_, level_ + 1);
    if (!result_) result_ = TUPLE(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SIMPLE_VALUE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // STRING_BRACKET STRING_INNER_CONTENT? STRING_BRACKET
  public static boolean STRING(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING")) return false;
    if (!nextTokenIs(builder_, STRING_BRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_BRACKET);
    result_ = result_ && STRING_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, STRING_BRACKET);
    exit_section_(builder_, marker_, STRING, result_);
    return result_;
  }

  // STRING_INNER_CONTENT?
  private static boolean STRING_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING_1")) return false;
    STRING_INNER_CONTENT(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // STRING_CONTENT (SEQUENCE_NUMBERS? LINE_START STRING_INNER_CONTENT)*
  public static boolean STRING_INNER_CONTENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING_INNER_CONTENT")) return false;
    if (!nextTokenIs(builder_, STRING_CONTENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_CONTENT);
    result_ = result_ && STRING_INNER_CONTENT_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, STRING_INNER_CONTENT, result_);
    return result_;
  }

  // (SEQUENCE_NUMBERS? LINE_START STRING_INNER_CONTENT)*
  private static boolean STRING_INNER_CONTENT_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING_INNER_CONTENT_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!STRING_INNER_CONTENT_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "STRING_INNER_CONTENT_1", pos_)) break;
    }
    return true;
  }

  // SEQUENCE_NUMBERS? LINE_START STRING_INNER_CONTENT
  private static boolean STRING_INNER_CONTENT_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING_INNER_CONTENT_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = STRING_INNER_CONTENT_1_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LINE_START);
    result_ = result_ && STRING_INNER_CONTENT(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // SEQUENCE_NUMBERS?
  private static boolean STRING_INNER_CONTENT_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "STRING_INNER_CONTENT_1_0_0")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  /* ********************************************************** */
  // TUPLE_START TUPLE_INNER_CONTENT? TUPLE_END
  public static boolean TUPLE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE")) return false;
    if (!nextTokenIs(builder_, TUPLE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TUPLE_START);
    result_ = result_ && TUPLE_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, TUPLE_END);
    exit_section_(builder_, marker_, TUPLE, result_);
    return result_;
  }

  // TUPLE_INNER_CONTENT?
  private static boolean TUPLE_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_1")) return false;
    TUPLE_INNER_CONTENT(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (TUPLE_PARAM? PARAM_EQUALS? TUPLE_PARAM? TUPLE_PARAM_DELIM COMMENT? SEQUENCE_NUMBERS? LINE_START?)* TUPLE_PARAM? PARAM_EQUALS? TUPLE_PARAM?
  public static boolean TUPLE_INNER_CONTENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TUPLE_INNER_CONTENT, "<tuple inner content>");
    result_ = TUPLE_INNER_CONTENT_0(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_1(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_2(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_3(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (TUPLE_PARAM? PARAM_EQUALS? TUPLE_PARAM? TUPLE_PARAM_DELIM COMMENT? SEQUENCE_NUMBERS? LINE_START?)*
  private static boolean TUPLE_INNER_CONTENT_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!TUPLE_INNER_CONTENT_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "TUPLE_INNER_CONTENT_0", pos_)) break;
    }
    return true;
  }

  // TUPLE_PARAM? PARAM_EQUALS? TUPLE_PARAM? TUPLE_PARAM_DELIM COMMENT? SEQUENCE_NUMBERS? LINE_START?
  private static boolean TUPLE_INNER_CONTENT_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = TUPLE_INNER_CONTENT_0_0_0(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_0_0_1(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_0_0_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, TUPLE_PARAM_DELIM);
    result_ = result_ && TUPLE_INNER_CONTENT_0_0_4(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_0_0_5(builder_, level_ + 1);
    result_ = result_ && TUPLE_INNER_CONTENT_0_0_6(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // TUPLE_PARAM?
  private static boolean TUPLE_INNER_CONTENT_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_0")) return false;
    TUPLE_PARAM(builder_, level_ + 1);
    return true;
  }

  // PARAM_EQUALS?
  private static boolean TUPLE_INNER_CONTENT_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_1")) return false;
    consumeToken(builder_, PARAM_EQUALS);
    return true;
  }

  // TUPLE_PARAM?
  private static boolean TUPLE_INNER_CONTENT_0_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_2")) return false;
    TUPLE_PARAM(builder_, level_ + 1);
    return true;
  }

  // COMMENT?
  private static boolean TUPLE_INNER_CONTENT_0_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_4")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  // SEQUENCE_NUMBERS?
  private static boolean TUPLE_INNER_CONTENT_0_0_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_5")) return false;
    consumeToken(builder_, SEQUENCE_NUMBERS);
    return true;
  }

  // LINE_START?
  private static boolean TUPLE_INNER_CONTENT_0_0_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_0_0_6")) return false;
    consumeToken(builder_, LINE_START);
    return true;
  }

  // TUPLE_PARAM?
  private static boolean TUPLE_INNER_CONTENT_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_1")) return false;
    TUPLE_PARAM(builder_, level_ + 1);
    return true;
  }

  // PARAM_EQUALS?
  private static boolean TUPLE_INNER_CONTENT_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_2")) return false;
    consumeToken(builder_, PARAM_EQUALS);
    return true;
  }

  // TUPLE_PARAM?
  private static boolean TUPLE_INNER_CONTENT_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_INNER_CONTENT_3")) return false;
    TUPLE_PARAM(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // STRING | TUPLE | SIMPLE_VALUE
  public static boolean TUPLE_PARAM(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TUPLE_PARAM")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TUPLE_PARAM, "<tuple param>");
    result_ = STRING(builder_, level_ + 1);
    if (!result_) result_ = TUPLE(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SIMPLE_VALUE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // JCL_SYNTAX*
  static boolean jclFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jclFile")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!JCL_SYNTAX(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "jclFile", pos_)) break;
    }
    return true;
  }

}
