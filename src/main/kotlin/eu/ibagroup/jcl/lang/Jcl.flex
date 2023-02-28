package eu.ibagroup.jcl.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import eu.ibagroup.jcl.lang.psi.JclTypes;
import com.intellij.psi.TokenType;import groovyjarjarantlr.Token;

%%

%class JclLexer
%implements FlexLexer
%line
%column
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}
%{
  public int yyline = 0;
  public int yycolumn = 0;
  public int prevState = 0;
  public IElementType prevType = null;
  public boolean movedBack = false;
  public boolean instreamParamStarted = false;
  public boolean instreamStartedWithData = false;
  public boolean firstParamInitialized = false;
  public boolean canCommentContinue = false;
  public boolean isIfConditionContinues = false;
  public int tupleInnerCounter = 0;
  public void moveBackTo(int position) {
      yypushback(Math.min(yycolumn+yylength()-position, yylength()));
  }
  public boolean isSequenceNumberOrWhiteSpace (IElementType elementType) {
      return elementType == JclTypes.SEQUENCE_NUMBERS
       || elementType == TokenType.WHITE_SPACE
       || elementType == JclTypes.CRLF;
  }
  public boolean isParamKeyOrValue (IElementType elementType) {
      return elementType == JclTypes.PARAM_KEY
       || elementType == JclTypes.SIMPLE_VALUE;
  }
  public IElementType jclBegin(int state, IElementType elementType) {
      if (elementType == JclTypes.LINE_START && yycolumn != 0) {
          yybegin(state);
          return TokenType.BAD_CHARACTER;
      }
      if (elementType == JclTypes.COMMENT && yycolumn < 71) {
        if (yycolumn+yylength() > 71) {
          canCommentContinue = yytext().charAt(71-yycolumn) != ' ';
        } else {
          canCommentContinue = false;
        }
      }
      if (71 < yycolumn+yylength() && yycolumn < 79 && !isSequenceNumberOrWhiteSpace(elementType)) {
          moveBackTo(71);
          if (yycolumn!=71) {
            prevState = state;
          }
          yybegin(WAITING_SN);
          return elementType;
      }

      if (elementType != JclTypes.CRLF) {
        if (yycolumn+yylength() > 79 && !movedBack) {
          moveBackTo(79);
          movedBack = true;
          yybegin(prevState);
          return elementType;
        }
        if (movedBack) {
          movedBack = false;
          yybegin(state);
          return TokenType.BAD_CHARACTER;
        }
      }
      if (prevType == TokenType.WHITE_SPACE && isParamKeyOrValue(elementType)) {
          if (firstParamInitialized && (yycolumn < 3 || yycolumn > 15)) {
              return TokenType.BAD_CHARACTER;
          }
      } else if (prevType == JclTypes.LINE_START && elementType == JclTypes.STRING_CONTENT) {
          if (firstParamInitialized) {
              int startChar = yytext().toString().indexOf(yytext().toString().trim());
              if (startChar == 0 || startChar > 13) {
              return TokenType.BAD_CHARACTER;
              }
          }
      } else if (prevType == TokenType.WHITE_SPACE && elementType == JclTypes.PROPERTY_NAME) {
          if (isIfConditionContinues) {
              if (yycolumn < 3 || yycolumn > 15) {
                  return TokenType.BAD_CHARACTER;
              }
              isIfConditionContinues = false;
          }
      }

      prevType = elementType;
      prevState = state;
      yybegin(state);
      return elementType == JclTypes.CRLF ? TokenType.WHITE_SPACE : elementType;
  }
  public IElementType endParams(IElementType elementType) {
      firstParamInitialized = false;
      if (instreamParamStarted) {
          instreamParamStarted = false;
          if (elementType != JclTypes.CRLF){
              return jclBegin(WAITING_INSTREAM_CONTINUES, elementType);
          } else {
              return jclBegin(INSTREAM_LINE_CONTINUED, elementType);
          }
      } else {
          if (elementType != JclTypes.CRLF) {
              return jclBegin(WAITING_NEW_LINE, elementType);
          } else {
              return jclBegin(YYINITIAL, elementType);
          }
      }
  }
  public IElementType processParamDelim () {
      if (yycolumn != 71) {
          firstParamInitialized = true;
          return jclBegin(WAITING_PARAM, JclTypes.PARAM_DELIM);
      } else {
          moveBackTo(71);
          prevState = WAITING_PARAM_DELIM;
          yybegin(WAITING_SN);
          return TokenType.WHITE_SPACE;
      }
  }
  public IElementType processSimpleParam () {
      if ((yytext().toString().equals("*") && yycolumn <= 71) ||
            (yytext().toString().startsWith("*") && yycolumn == 71)
      ) {
          instreamParamStarted = true;
          return jclBegin(WAITING_PARAM_DELIM, JclTypes.INSTREAM_START);
      } else if ((yytext().toString().equals("DATA") && yycolumn <= 68) ||
                    (yytext().toString().startsWith("DATA") && yycolumn == 68)) {
          instreamParamStarted = true;
          instreamStartedWithData = true;
          return jclBegin(WAITING_PARAM_DELIM, JclTypes.INSTREAM_START);
      } else if (yycolumn <= 70) {
          return jclBegin(WAITING_EQUALS_OR_DELIM, JclTypes.PARAM_KEY);
      } else {
          moveBackTo(71);
          prevState = WAITING_PARAM;
          yybegin(WAITING_SN);
          return TokenType.WHITE_SPACE;
      }
  }
  public IElementType processInstreamEnd() {
      if (!instreamStartedWithData) {
          return jclBegin(LINE_STARTED, JclTypes.LINE_START);
      }
      else {
          moveBackTo(0);
          return jclBegin(WAITING_INSTREAM_DATA_LINE, TokenType.WHITE_SPACE);
      }
  }
  public IElementType processStringEnd () {
      if (tupleInnerCounter <= 0) {
          tupleInnerCounter = 0;
          return jclBegin(WAITING_PARAM_DELIM, JclTypes.STRING_BRACKET);
      } else {
          return jclBegin(WAITING_TUPLE_PARAM_DELIM, JclTypes.STRING_BRACKET);
      }
  }
  public IElementType processTupleEnd () {
      --tupleInnerCounter;
      if (tupleInnerCounter <= 0) {
          tupleInnerCounter = 0;
          return jclBegin(WAITING_PARAM_DELIM, JclTypes.TUPLE_END);
      } else {
          return jclBegin(WAITING_TUPLE_PARAM_DELIM, JclTypes.TUPLE_END);
      }
  }
%}

CRLF=\R
SPACE=[\ \t\f]+
STRING_CONTENT=([^\n\r\']|\'\')*
MF_IDENTIFIER_NAME=[^,=\*\ \n\f\t\/\\\.]{1,8}
MF_OPERATOR_NAME=[^,=\*\ \n\f\t\/\\\.]{1,10}
IF_OPERATOR=IF
THEN_OPERATOR=THEN
ENDIF_OPERATOR=ENDIF
IF_VALUE=[^\ \n\f\t\\,=\'\(\)\|\&><=¬\.0-9][^\ \n\f\t\\,=\'\(\)\|\&><=¬\.]*
IF_VALUE_DIGITAL=[0-9]+
IF_LOPERATOR=&|\|
IF_LOPERATOR_DECLARATIVE=AND|OR
IF_ROPERATOR=>|<|=|>=|<=|¬=|¬>|¬<
IF_ROPERATOR_DECLARATIVE=GT|LT|EQ|GE|LE|NE|NG|NL
IF_OPERATOR_NOT=¬
PARAM_VALUE=[^\ \n\f\t\\,\'\(\)]{1}[^\ \n\f\t\\,\']*
NOT_EQUALS_NOT_SPACE=[^\ \n\f\t\\,=\'\(\)]+
END_OF_LINE_COMMENT=\/\/\*[^\n\r]*
INSTREAM_SIMPLE_LINE=\/|(\/[^\/\n\r][^\n\r]{0,70})|([^\/][^\n\r]{0,71})
INSTREAM_DATA_LINE=\/\/[^\n\r]{0,70}
LINE_START=\/\/
TEMPLATE_PARAM=(\{\{.+\}\})
STRING=\'.*\'
STRING_BRACKET=\'
//tuple inside tuple ((1,2,3),123)
//TUPLE=\( (.+ | \(.+ (,.+)\)) (,(.+ | \(.+ (,.+)\))) \)
TUPLE_START=\(
TUPLE_END=\)
EQUALS==
PARAM_DELIM=,
SEQUENCE_NUMBER=[^\n\r]{1,8}
SN_STARTS_WITH_NOT_SPACE=[^\n\r\t\ \f]{1}[^\n\r]{0,7}
INSTREAM_START=\*|DATA
DOT=\.
INSTREAM_END=\/\*
NULL_STATEMENT_SPACE=[\ \t\f]{70,78}
COMMENT=[^\n\r]+
COMMENT_CONTINUES_LINE=\/\/\ [^\n\r]+


%state LINE_STARTED
%state WAITING_OPERATOR
%state WAITING_OPERATOR_OR_OVERRIDE_NAME
%state WAITING_OVERRIDE_NAME
%state WAITING_INSTREAM_DATA_LINE

%state WAITING_SPACE_BEFORE_IF_CONDITION
%state WAITING_IF_CONDITION_START_OR_VALUE
%state WAITING_IF_CONDITION_VALUE
%state WAITING_IF_LINE_CONTUNUES
%state WAITING_IF_CONDITION_VALUE_AFTER_RO
%state WAITING_IF_CONDITION_LOGICAL_VALUE
%state WAITING_IF_CONDITION_VALUE_OR_SPACE
%state WAITING_IF_CONDITION_VALUE_OR_SPACE_AFTER_RO
%state WAITING_SPACE_BEFORE_IF_CONDITION_VALUE
%state WAITING_SPACE_BEFORE_IF_CONDITION_VALUE_AFTER_RO
%state WAITING_IF_CONDITION_VALUE_OR_IF_END
%state WAITING_IF_OPERATOR
%state WAITING_IF_LOPERATOR
%state WAITING_IF_OPERATOR_OR_DOT
%state WAITING_IF_LOPERATOR_OR_DOT
%state WAITING_SPACE_OR_IF_OPERATOR
%state WAITING_SPACE_OR_IF_LOPERATOR
%state WAITING_THEN_OPERATOR_SPACE
%state WAITING_THEN_OPERATOR

%state WAITING_SPACE_PARAMS
%state WAITING_SPACE_PARAMS_OR_INSTREAM
%state WAITING_PARAM
%state WAITING_PARAM_OR_INSTREAM
%state WAITING_STRING_CONTENT
%state WAITING_STRING_CONTENT_OR_STRING_END
%state WAITING_STRING_END_OR_CONTINUES
%state WAITING_TUPLE_PARAM
%state WAITING_TUPLE_PARAM_DELIM
%state WAITING_TUPLE_PARAM_DELIM_OR_EQUALS
%state WAITING_TUPLE_PARAM_AFTER_EQUALS
%state WAITING_TUPLE_PARAM_CONTINUES
%state WAITING_TUPLE_PARAM_NEW_LINE
%state WAITING_TUPLE_PARAM_NEW_LINE_OR_SN
%state WAITING_SPACE_BEFORE_TUPLE_PARAM
%state STRING_LINE_CONTINUED
%state WAITING_EQUALS_OR_DELIM
%state WAITING_PARAM_VALUE
%state WAITING_PARAM_DELIM
%state WAITING_PARAM_DELIM_AFTER_INSTREAM_START

%state WAITING_NEW_LINE
%state WAITING_LINE_CONTINUES

%state WAITING_INSTREAM
%state WAITING_INSTREAM_CONTINUES
%state LINE_CONTINUED
%state INSTREAM_LINE_CONTINUED

%state WAITING_COMMENT_CONTINUED_LINE
%state WAITING_COMMENT_CONTINUED_WHEN_PARAMS_NOT_ENDED

%state WAITING_SN
%state WAITING_SN_OR_NEW_LINE
%state WAITING_SN_OR_LINE_CONTINUES

%%

<YYINITIAL> {LINE_START}                                    { return jclBegin(LINE_STARTED, JclTypes.LINE_START); }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { return jclBegin(WAITING_SN_OR_NEW_LINE, JclTypes.COMMENT); }

<LINE_STARTED> {MF_IDENTIFIER_NAME}                         { return jclBegin(WAITING_OPERATOR_OR_OVERRIDE_NAME, JclTypes.OPERATOR_NAME); }

<WAITING_OPERATOR_OR_OVERRIDE_NAME> {DOT}                   { return jclBegin(WAITING_OVERRIDE_NAME, JclTypes.DOT); }

<WAITING_OVERRIDE_NAME> {MF_IDENTIFIER_NAME}                { return jclBegin(WAITING_OPERATOR, JclTypes.OPERATOR_OVERRIDE_NAME); }

<WAITING_OVERRIDE_NAME> {SPACE}                             { return jclBegin(WAITING_OPERATOR, TokenType.BAD_CHARACTER); }

<LINE_STARTED> {NULL_STATEMENT_SPACE}                       { return jclBegin(WAITING_SN, TokenType.WHITE_SPACE); }

<LINE_STARTED> {SPACE}                                      { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<LINE_CONTINUED> {LINE_START}                               { return jclBegin(WAITING_SPACE_PARAMS, JclTypes.LINE_START); }

<LINE_CONTINUED> {END_OF_LINE_COMMENT}                      { return jclBegin(WAITING_LINE_CONTINUES, JclTypes.COMMENT); }

<INSTREAM_LINE_CONTINUED> {END_OF_LINE_COMMENT}             { return jclBegin(WAITING_INSTREAM_CONTINUES, JclTypes.COMMENT); }

<INSTREAM_LINE_CONTINUED> {INSTREAM_END}                    { instreamStartedWithData = false; return jclBegin(WAITING_NEW_LINE, JclTypes.INSTREAM_END); }

<INSTREAM_LINE_CONTINUED> {LINE_START}                      { return processInstreamEnd(); }

<INSTREAM_LINE_CONTINUED> {INSTREAM_SIMPLE_LINE}            { return jclBegin(WAITING_INSTREAM_CONTINUES, JclTypes.INSTREAM_TEXT); }

<WAITING_INSTREAM_DATA_LINE> {INSTREAM_DATA_LINE}           { return jclBegin(WAITING_INSTREAM_CONTINUES, JclTypes.INSTREAM_TEXT); }


<WAITING_OPERATOR,
 WAITING_OPERATOR_OR_OVERRIDE_NAME> {SPACE}                 { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<WAITING_OPERATOR> {IF_OPERATOR}                            { return jclBegin(WAITING_SPACE_BEFORE_IF_CONDITION, JclTypes.IF_OPERATOR); }

<WAITING_OPERATOR> {ENDIF_OPERATOR}                         { return jclBegin(WAITING_NEW_LINE, JclTypes.END_IF); }

<WAITING_OPERATOR> {MF_OPERATOR_NAME}                       { return jclBegin(WAITING_SPACE_PARAMS_OR_INSTREAM, JclTypes.OPERATOR); }

//<WAITING_OPERATOR> {DD_OPERATOR}                            { return jclBegin(WAITING_SPACE_PARAMS_OR_INSTREAM, JclTypes.OPERATOR); }




<WAITING_SPACE_BEFORE_IF_CONDITION> {SPACE}                 { return jclBegin(WAITING_IF_CONDITION_START_OR_VALUE, TokenType.WHITE_SPACE); }

<WAITING_IF_CONDITION_VALUE,
 WAITING_IF_CONDITION_VALUE_OR_SPACE,
 WAITING_IF_CONDITION_START_OR_VALUE> {IF_OPERATOR_NOT}     { return jclBegin(WAITING_IF_CONDITION_LOGICAL_VALUE, JclTypes.IF_OPERATOR_NOT); }

<WAITING_IF_CONDITION_VALUE,
 WAITING_IF_CONDITION_VALUE_OR_SPACE,
 WAITING_IF_CONDITION_START_OR_VALUE,
 WAITING_IF_CONDITION_LOGICAL_VALUE> {TUPLE_START}          { return jclBegin(WAITING_IF_CONDITION_VALUE_OR_SPACE, JclTypes.IF_CONDITION_START); }

<WAITING_IF_CONDITION_VALUE_OR_SPACE> {SPACE}               { return jclBegin(WAITING_IF_CONDITION_VALUE, TokenType.WHITE_SPACE); }

<WAITING_IF_CONDITION_VALUE_OR_SPACE_AFTER_RO> {SPACE}      { return jclBegin(WAITING_IF_CONDITION_VALUE_AFTER_RO, TokenType.WHITE_SPACE); }

<WAITING_IF_CONDITION_VALUE,
 WAITING_IF_CONDITION_VALUE_AFTER_RO,
 WAITING_IF_CONDITION_VALUE_OR_SPACE,
 WAITING_IF_CONDITION_VALUE_OR_SPACE_AFTER_RO,
 WAITING_IF_CONDITION_START_OR_VALUE> {IF_VALUE}            { return jclBegin(WAITING_IF_OPERATOR_OR_DOT, JclTypes.PROPERTY_NAME); }

<WAITING_IF_CONDITION_LOGICAL_VALUE> {IF_VALUE}             { return jclBegin(WAITING_IF_LOPERATOR_OR_DOT, JclTypes.PROPERTY_NAME); }

<WAITING_IF_CONDITION_VALUE_AFTER_RO,
 WAITING_IF_CONDITION_VALUE_OR_SPACE_AFTER_RO,
 WAITING_IF_CONDITION_VALUE_OR_SPACE> {IF_VALUE_DIGITAL}    { return jclBegin(WAITING_IF_OPERATOR, JclTypes.PROPERTY_NAME); }

<WAITING_IF_OPERATOR_OR_DOT> {DOT}                          { return jclBegin(WAITING_IF_CONDITION_VALUE, JclTypes.DOT); }

<WAITING_IF_LOPERATOR_OR_DOT> {DOT}                         { return jclBegin(WAITING_IF_CONDITION_LOGICAL_VALUE, JclTypes.DOT); }

<WAITING_IF_LOPERATOR,
 WAITING_IF_LOPERATOR_OR_DOT> {SPACE}                       { return jclBegin(WAITING_IF_LOPERATOR, TokenType.WHITE_SPACE); }

<WAITING_IF_OPERATOR,
 WAITING_IF_OPERATOR_OR_DOT> {SPACE}                        { return jclBegin(WAITING_IF_OPERATOR, TokenType.WHITE_SPACE); }

<WAITING_SPACE_OR_IF_LOPERATOR> {SPACE}                     { return jclBegin(WAITING_IF_LOPERATOR, TokenType.WHITE_SPACE); }

<WAITING_SPACE_OR_IF_LOPERATOR> {TUPLE_END}                 { return jclBegin(WAITING_IF_LOPERATOR, JclTypes.IF_CONDITION_END); }

<WAITING_IF_OPERATOR,
 WAITING_IF_OPERATOR_OR_DOT> {IF_ROPERATOR}                 { return jclBegin(WAITING_IF_CONDITION_VALUE_OR_SPACE_AFTER_RO, JclTypes.IF_CONDITION_OPERATOR); }

<WAITING_IF_OPERATOR> {IF_ROPERATOR_DECLARATIVE}            { return jclBegin(WAITING_SPACE_BEFORE_IF_CONDITION_VALUE_AFTER_RO, JclTypes.IF_CONDITION_OPERATOR); }

<WAITING_IF_OPERATOR,
 WAITING_IF_LOPERATOR> {IF_LOPERATOR}                       { return jclBegin(WAITING_SPACE_BEFORE_IF_CONDITION_VALUE, JclTypes.IF_CONDITION_OPERATOR); }

<WAITING_IF_OPERATOR,
 WAITING_IF_LOPERATOR> {IF_LOPERATOR_DECLARATIVE}           { return jclBegin(WAITING_SPACE_BEFORE_IF_CONDITION_VALUE, JclTypes.IF_CONDITION_OPERATOR); }

<WAITING_SPACE_BEFORE_IF_CONDITION_VALUE> {SPACE}           { return jclBegin(WAITING_IF_CONDITION_VALUE, TokenType.WHITE_SPACE); }

<WAITING_SPACE_BEFORE_IF_CONDITION_VALUE_AFTER_RO> {SPACE}  { return jclBegin(WAITING_IF_CONDITION_VALUE_AFTER_RO, TokenType.WHITE_SPACE); }

<WAITING_SPACE_BEFORE_IF_CONDITION_VALUE> {SPACE}*{CRLF}    { isIfConditionContinues = true; return jclBegin(WAITING_IF_LINE_CONTUNUES, TokenType.WHITE_SPACE); }

<WAITING_IF_LINE_CONTUNUES> {LINE_START}                    { return jclBegin(WAITING_SPACE_BEFORE_IF_CONDITION_VALUE, JclTypes.LINE_START); }

<WAITING_IF_OPERATOR,
 WAITING_IF_OPERATOR_OR_DOT> {TUPLE_END}                    { return jclBegin(WAITING_SPACE_OR_IF_LOPERATOR, JclTypes.IF_CONDITION_END); }

<WAITING_IF_OPERATOR,
 WAITING_IF_LOPERATOR> {THEN_OPERATOR}                      { return jclBegin(WAITING_NEW_LINE, JclTypes.THEN_OPERATOR); }

//<WAITING_THEN_OPERATOR_SPACE> {SPACE}                       { return jclBegin(WAITING_THEN_OPERATOR, TokenType.WHITE_SPACE); }
//
//<WAITING_THEN_OPERATOR> {THEN_OPERATOR}                     { return jclBegin(WAITING_NEW_LINE, JclTypes.THEN_OPERATOR); }









<WAITING_SPACE_PARAMS> {SPACE}                              { return jclBegin(WAITING_PARAM, TokenType.WHITE_SPACE); }

<WAITING_SPACE_PARAMS_OR_INSTREAM> {SPACE}                  { return jclBegin(WAITING_PARAM, TokenType.WHITE_SPACE); }

<WAITING_SPACE_PARAMS_OR_INSTREAM> {SPACE}*{CRLF}           { if (instreamParamStarted) return jclBegin(INSTREAM_LINE_CONTINUED, TokenType.WHITE_SPACE); else return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }

<WAITING_NEW_LINE> {SPACE}+{CRLF}                           { return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }

<WAITING_LINE_CONTINUES> {SPACE}+{CRLF}                     { return jclBegin(LINE_CONTINUED, TokenType.WHITE_SPACE); }

<WAITING_INSTREAM_CONTINUES> {CRLF}+                        { return jclBegin(INSTREAM_LINE_CONTINUED, JclTypes.CRLF); }

<WAITING_INSTREAM_CONTINUES> {SPACE}{CRLF}                  { return jclBegin(INSTREAM_LINE_CONTINUED, TokenType.WHITE_SPACE); }

<WAITING_NEW_LINE> {CRLF}                                   { return jclBegin(YYINITIAL, JclTypes.CRLF); }

<WAITING_LINE_CONTINUES> {CRLF}                             { return jclBegin(LINE_CONTINUED, JclTypes.CRLF); }





//<WAITING_PARAM_OR_INSTREAM> {INSTREAM_START}                { instreamParamStarted = true; return jclBegin(WAITING_PARAM_DELIM_AFTER_INSTREAM_START, JclTypes.INSTREAM_START); }

<WAITING_INSTREAM> {SPACE}                                  { return jclBegin(WAITING_INSTREAM_CONTINUES, TokenType.WHITE_SPACE); }

<WAITING_INSTREAM> {CRLF}                                   { return jclBegin(INSTREAM_LINE_CONTINUED, JclTypes.CRLF); }



<WAITING_PARAM> {SPACE}                                     { return jclBegin(WAITING_LINE_CONTINUES, TokenType.WHITE_SPACE); }

<WAITING_PARAM> {SPACE}*{CRLF}                              { if (firstParamInitialized) return jclBegin(LINE_CONTINUED, TokenType.WHITE_SPACE); else return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }



<WAITING_PARAM> {STRING_BRACKET}                            { return jclBegin(WAITING_STRING_CONTENT_OR_STRING_END, JclTypes.STRING_BRACKET); }

<WAITING_STRING_CONTENT,
 WAITING_STRING_CONTENT_OR_STRING_END> {STRING_CONTENT}     { return jclBegin(WAITING_STRING_END_OR_CONTINUES, JclTypes.STRING_CONTENT); }

<WAITING_STRING_END_OR_CONTINUES,
 WAITING_STRING_CONTENT_OR_STRING_END> {STRING_BRACKET}     { return processStringEnd(); }

<WAITING_STRING_END_OR_CONTINUES> {CRLF}                    { return jclBegin(STRING_LINE_CONTINUED, TokenType.WHITE_SPACE); }

<STRING_LINE_CONTINUED> {LINE_START}                        { return jclBegin(WAITING_STRING_CONTENT_OR_STRING_END, JclTypes.LINE_START); }



<WAITING_PARAM,
 WAITING_TUPLE_PARAM> {TUPLE_START}                         { ++tupleInnerCounter; return jclBegin(WAITING_TUPLE_PARAM, JclTypes.TUPLE_START); }

<WAITING_TUPLE_PARAM> {STRING_BRACKET}                      { return jclBegin(WAITING_STRING_CONTENT_OR_STRING_END, JclTypes.STRING_BRACKET); }

<WAITING_TUPLE_PARAM> {NOT_EQUALS_NOT_SPACE}                { return jclBegin(WAITING_TUPLE_PARAM_DELIM_OR_EQUALS, JclTypes.SIMPLE_VALUE); }

<WAITING_TUPLE_PARAM> {TUPLE_END}                           { return processTupleEnd(); }

<WAITING_TUPLE_PARAM_DELIM,
WAITING_TUPLE_PARAM_DELIM_OR_EQUALS,
 WAITING_TUPLE_PARAM> {PARAM_DELIM}                         { return jclBegin(WAITING_TUPLE_PARAM, JclTypes.TUPLE_PARAM_DELIM); }

<WAITING_TUPLE_PARAM_DELIM,
WAITING_TUPLE_PARAM_DELIM_OR_EQUALS> {TUPLE_END}            { return processTupleEnd(); }

<WAITING_TUPLE_PARAM_DELIM_OR_EQUALS> {EQUALS}              { return jclBegin(WAITING_TUPLE_PARAM_AFTER_EQUALS, JclTypes.PARAM_EQUALS); }

<WAITING_TUPLE_PARAM_AFTER_EQUALS> {NOT_EQUALS_NOT_SPACE}   { return jclBegin(WAITING_TUPLE_PARAM_DELIM, JclTypes.SIMPLE_VALUE); }

<WAITING_TUPLE_PARAM> {SPACE}                               { return jclBegin(WAITING_LINE_CONTINUES, TokenType.WHITE_SPACE); }

<WAITING_TUPLE_PARAM,
 WAITING_TUPLE_PARAM_NEW_LINE> {CRLF}                       { return jclBegin(WAITING_TUPLE_PARAM_CONTINUES, TokenType.WHITE_SPACE); }

<WAITING_TUPLE_PARAM_NEW_LINE_OR_SN> {SEQUENCE_NUMBER}      { return jclBegin(WAITING_TUPLE_PARAM_NEW_LINE, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_TUPLE_PARAM_CONTINUES> {LINE_START}                { return jclBegin(WAITING_SPACE_BEFORE_TUPLE_PARAM, JclTypes.LINE_START); }

<WAITING_SPACE_BEFORE_TUPLE_PARAM> {SPACE}                  { return jclBegin(WAITING_TUPLE_PARAM, TokenType.WHITE_SPACE); }




<WAITING_PARAM> {TEMPLATE_PARAM}                            { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TEMPLATE_PARAM); }

<WAITING_PARAM> {NOT_EQUALS_NOT_SPACE}                      { return processSimpleParam(); }

<WAITING_EQUALS_OR_DELIM> {EQUALS}                          { return jclBegin(WAITING_PARAM_VALUE, JclTypes.PARAM_EQUALS); }

<WAITING_PARAM_VALUE> {STRING_BRACKET}                      { return jclBegin(WAITING_STRING_CONTENT, JclTypes.STRING_BRACKET); }

<WAITING_PARAM_VALUE,
 WAITING_PARAM> {TUPLE_START}                               { ++tupleInnerCounter; return jclBegin(WAITING_TUPLE_PARAM, JclTypes.TUPLE_START); }

<WAITING_PARAM_VALUE> {PARAM_VALUE}                         { return jclBegin(WAITING_PARAM_DELIM, JclTypes.SIMPLE_VALUE); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM,
 WAITING_PARAM_VALUE,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {PARAM_DELIM}    { return processParamDelim(); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM_VALUE,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {SPACE}          { return endParams(TokenType.WHITE_SPACE); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM_VALUE,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {CRLF}           { return endParams(JclTypes.CRLF); }

// TODO: think about how to rework.
//<WAITING_PARAM_DELIM> {SN_STARTS_WITH_NOT_SPACE}            { return endParams(JclTypes.SEQUENCE_NUMBERS); }


<WAITING_EQUALS_OR_DELIM> {PARAM_DELIM}                     { return processParamDelim(); }

<WAITING_EQUALS_OR_DELIM> {SPACE}                           { return endParams(TokenType.WHITE_SPACE); }

<WAITING_EQUALS_OR_DELIM> {CRLF}                            { return endParams(JclTypes.CRLF); }


<WAITING_NEW_LINE> {COMMENT}                                { return jclBegin(WAITING_SN_OR_NEW_LINE, JclTypes.COMMENT); }

<WAITING_LINE_CONTINUES> {COMMENT}                          { return jclBegin(WAITING_SN_OR_LINE_CONTINUES, JclTypes.COMMENT); }

<WAITING_COMMENT_CONTINUED_LINE> {COMMENT_CONTINUES_LINE}   { return jclBegin(WAITING_SN_OR_NEW_LINE, JclTypes.COMMENT); }

<WAITING_COMMENT_CONTINUED_WHEN_PARAMS_NOT_ENDED> {COMMENT_CONTINUES_LINE}   { return jclBegin(WAITING_SN_OR_LINE_CONTINUES, JclTypes.COMMENT); }


<WAITING_SN> {SEQUENCE_NUMBER}                              { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_INSTREAM_CONTINUES> {SPACE}?{SEQUENCE_NUMBER}      { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_SN_OR_NEW_LINE> {SEQUENCE_NUMBER}                  { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_SN_OR_NEW_LINE> {SPACE}+{CRLF}                     { return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }

<WAITING_SN_OR_NEW_LINE> {CRLF}                             { if (canCommentContinue) return jclBegin(WAITING_COMMENT_CONTINUED_LINE, JclTypes.CRLF); else return jclBegin(YYINITIAL, JclTypes.CRLF); }

<WAITING_SN_OR_LINE_CONTINUES> {SEQUENCE_NUMBER}            { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_SN_OR_LINE_CONTINUES> {SPACE}+{CRLF}               { return jclBegin(LINE_CONTINUED, TokenType.WHITE_SPACE); }

<WAITING_SN_OR_LINE_CONTINUES> {CRLF}                       { if (canCommentContinue) return jclBegin(WAITING_COMMENT_CONTINUED_WHEN_PARAMS_NOT_ENDED, JclTypes.CRLF); else return jclBegin(LINE_CONTINUED, JclTypes.CRLF); }

{CRLF}+                                                     {
          if (instreamParamStarted) {
              yybegin(INSTREAM_LINE_CONTINUED);
          } else if (isIfConditionContinues) {
              yybegin(WAITING_IF_CONDITION_VALUE);
          } else {
              yybegin(YYINITIAL);
          }
          return TokenType.WHITE_SPACE;
      }

[^]                                                         { return TokenType.BAD_CHARACTER; }
