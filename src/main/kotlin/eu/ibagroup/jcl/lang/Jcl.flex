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
  public boolean movedBack = false;
  public void moveBack(int count) {
      int moveBackCount = Math.min(yycolumn+yylength()-count, yylength());
      yypushback(moveBackCount);
  }
  public IElementType jclBegin(int state, IElementType elementType) {
      if (
              72 < yycolumn+yylength()
              && elementType != JclTypes.SEQUENCE_NUMBERS
              && elementType != JclTypes.COMMENT
              && yycolumn < 80
              && elementType != TokenType.WHITE_SPACE
              && elementType != JclTypes.CRLF) {
          moveBack(72);
          if (yycolumn!=72) {
            prevState = state;
          }
          yybegin(WAITING_SN);
          return elementType;
      }
      if (yycolumn < 72 && elementType == JclTypes.SEQUENCE_NUMBERS) {
          yypushback(yylength()-1);
          yycolumn-=yylength()-1;
          yybegin(state);
          return TokenType.BAD_CHARACTER;
      }

      if (elementType == JclTypes.SEQUENCE_NUMBERS) {
          state = prevState;
      }
      if (elementType != JclTypes.CRLF) {
        if (yycolumn +yylength() > 80 && !movedBack) {
          moveBack(80);
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

      prevState = state;
      yybegin(state);
      return elementType == JclTypes.CRLF ? TokenType.WHITE_SPACE : elementType;
  }
%}

CRLF=[\n\r\R]
WHITE_SPACE=[\ \n\t\f]+
SPACE=[\ \t\f]+
MF_IDENTIFIER_NAME=[A-Za-z]{1}[^,=\ \n\f\t\/\\]{1,7}
NOT_SPACE=[^\ \n\f\t\\,]+
NOT_EQUALS_NOT_SPACE=[^\ \n\f\t\\,=]+
OPERATOR=(JOB|EXEC|DD)
END_OF_LINE_COMMENT=\/\/\*[^\n\r\R]*
LINE_START=\/\/
TEMPLATE_PARAM=(\{\{.+\}\})
STRING=\'.*\'
//tuple inside tuple ((1,2,3),123)
TUPLE=\( (.+ | \(.+ (,.+)\)) (,(.+ | \(.+ (,.+)\))) \)
EQUALS==
PARAM_DELIM=,
SEQUENCE_NUMBER=[^\n\r]{1,8}

%state LINE_STARTED
%state WAITING_OPERATOR
%state WAITING_SPACE_PARAMS
%state WAITING_PARAM
%state WAITING_EQUALS_OR_DELIM
%state WAITING_PARAM_VALUE
%state WAITING_PARAM_DELIM
%state WAITING_NEW_LINE
%state WAITING_LINE_CONTINUES
%state LINE_CONTINUED

%state WAITING_SN
%state WAITING_SN_OR_NEW_LINE
%state WAITING_SN_OR_LINE_CONTINUES

%%

<YYINITIAL> {LINE_START}                                    { yybegin(LINE_STARTED); return JclTypes.LINE_START; }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { return jclBegin(WAITING_SN_OR_NEW_LINE, JclTypes.COMMENT); }

<LINE_STARTED> {MF_IDENTIFIER_NAME}                         { yybegin(WAITING_OPERATOR); return JclTypes.OPERATOR_NAME; }

<LINE_STARTED> {SPACE}                                      { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<LINE_CONTINUED> {LINE_START}                               { return jclBegin(WAITING_SPACE_PARAMS, JclTypes.LINE_START); }

<LINE_CONTINUED> {END_OF_LINE_COMMENT}                      { return jclBegin(WAITING_LINE_CONTINUES, JclTypes.COMMENT); }

<WAITING_OPERATOR> {SPACE}                                  { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<WAITING_OPERATOR> {OPERATOR}                               { return jclBegin(WAITING_SPACE_PARAMS, JclTypes.OPERATOR); }

<WAITING_SPACE_PARAMS> {SPACE}                              { return jclBegin(WAITING_PARAM, TokenType.WHITE_SPACE); }

<WAITING_NEW_LINE> {SPACE}+{CRLF}                           { return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }

<WAITING_LINE_CONTINUES> {SPACE}+{CRLF}                     { return jclBegin(LINE_CONTINUED, TokenType.WHITE_SPACE); }

<WAITING_NEW_LINE> {CRLF}                                   { return jclBegin(YYINITIAL, JclTypes.CRLF); }

<WAITING_LINE_CONTINUES> {CRLF}                             { return jclBegin(LINE_CONTINUED, JclTypes.CRLF); }


<WAITING_PARAM> {SPACE}                                     { return jclBegin(WAITING_LINE_CONTINUES, TokenType.WHITE_SPACE); }

<WAITING_PARAM> {SPACE}*{CRLF}                              { return jclBegin(LINE_CONTINUED, TokenType.WHITE_SPACE); }

<WAITING_PARAM> {STRING}                                    { return jclBegin(WAITING_PARAM_DELIM, JclTypes.STRING); }

<WAITING_PARAM> {TUPLE}                                     { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TUPLE); }

<WAITING_PARAM> {TEMPLATE_PARAM}                            { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TEMPLATE_PARAM); }

<WAITING_PARAM> {NOT_EQUALS_NOT_SPACE}                      { return jclBegin(WAITING_EQUALS_OR_DELIM, JclTypes.PARAM_KEY); }

<WAITING_EQUALS_OR_DELIM> {EQUALS}                          { return jclBegin(WAITING_PARAM_VALUE, JclTypes.PARAM_EQUALS); }

<WAITING_PARAM_VALUE> {STRING}                              { return jclBegin(WAITING_PARAM_DELIM, JclTypes.STRING); }

<WAITING_PARAM_VALUE> {TUPLE}                               { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TUPLE); }

<WAITING_PARAM_VALUE> {NOT_SPACE}                           { return jclBegin(WAITING_PARAM_DELIM, JclTypes.SIMPLE_VALUE); }

<WAITING_PARAM_DELIM> {PARAM_DELIM}                         { return jclBegin(WAITING_PARAM, JclTypes.PARAM_DELIM); }

<WAITING_PARAM_DELIM> {SPACE}                               { return jclBegin(WAITING_NEW_LINE, TokenType.WHITE_SPACE); }

<WAITING_EQUALS_OR_DELIM> {PARAM_DELIM}                     { return jclBegin(WAITING_PARAM, JclTypes.PARAM_DELIM); }

<WAITING_EQUALS_OR_DELIM> {SPACE}                           { return jclBegin(WAITING_NEW_LINE, TokenType.WHITE_SPACE); }

<WAITING_EQUALS_OR_DELIM> {CRLF}                            { return jclBegin(YYINITIAL, TokenType.WHITE_SPACE); }




<WAITING_SN> {SEQUENCE_NUMBER}                              { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_NEW_LINE> {SPACE}*{SEQUENCE_NUMBER}                { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_LINE_CONTINUES> {SPACE}*{SEQUENCE_NUMBER}          { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }



({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
