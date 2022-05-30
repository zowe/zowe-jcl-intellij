package eu.ibagroup.jcl.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import eu.ibagroup.jcl.lang.psi.JclTypes;
import com.intellij.psi.TokenType;

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
  public boolean instreamParamStarted = false;
  public boolean firstParamInitialized = false;
  public void moveBackTo(int position) {
      yypushback(Math.min(yycolumn+yylength()-position, yylength()));
  }
  public boolean isCommentOrWhiteSpace (IElementType elementType) {
      return elementType == JclTypes.SEQUENCE_NUMBERS
       || elementType == JclTypes.COMMENT
       || elementType == TokenType.WHITE_SPACE
       || elementType == JclTypes.CRLF;
  }
  public IElementType jclBegin(int state, IElementType elementType) {
      if (elementType == JclTypes.LINE_START && yycolumn != 0) {
          yybegin(state);
          return TokenType.BAD_CHARACTER;
      }
      if (72 < yycolumn+yylength() && yycolumn < 80 && !isCommentOrWhiteSpace(elementType)) {
          moveBackTo(72);
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

      if (elementType != JclTypes.CRLF) {
        if (yycolumn +yylength() > 80 && !movedBack) {
          moveBackTo(80);
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
  public IElementType startParams(boolean isInstreamParams, IElementType elementType) {
      instreamParamStarted = isInstreamParams;
      return jclBegin(WAITING_SPACE_PARAMS_OR_INSTREAM, elementType);
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
      if (yycolumn != 72) {
          firstParamInitialized = true;
          return jclBegin(WAITING_PARAM, JclTypes.PARAM_DELIM);
      } else {
          moveBackTo(72);
          prevState = WAITING_PARAM_DELIM;
          yybegin(WAITING_SN);
          return TokenType.WHITE_SPACE;
      }
  }
  public IElementType processSimpleParam () {
      if ((yytext().toString().equals("*") && yycolumn <= 71) || (yytext().toString().startsWith("*") && yycolumn == 71)) {
          instreamParamStarted = true;
          return jclBegin(WAITING_PARAM_DELIM, JclTypes.INSTREAM_START);
      } else if (yycolumn <= 71) {
          return jclBegin(WAITING_EQUALS_OR_DELIM, JclTypes.PARAM_KEY);
      } else {
          moveBackTo(72);
          prevState = WAITING_PARAM;
          yybegin(WAITING_SN);
          return TokenType.WHITE_SPACE;
      }
  }
%}

CRLF=\R
SPACE=[\ \t\f]+
MF_IDENTIFIER_NAME=[A-Za-z]{1}[^,=\*\ \n\f\t\/\\]{1,7}
NOT_SPACE=[^\ \n\f\t\\,]+
NOT_EQUALS_NOT_SPACE=[^\ \n\f\t\\,=]+
END_OF_LINE_COMMENT=\/\/\*[^\n\r]*
INSTREAM_SIMPLE_LINE=\/|(\/[^\/\n\r][^\n\r]{0,70})|([^\/][^\n\r]{0,71})
LINE_START=\/\/
TEMPLATE_PARAM=(\{\{.+\}\})
STRING=\'.*\'
//tuple inside tuple ((1,2,3),123)
TUPLE=\( (.+ | \(.+ (,.+)\)) (,(.+ | \(.+ (,.+)\))) \)
EQUALS==
PARAM_DELIM=,
SEQUENCE_NUMBER=[^\n\r]{1,8}
SN_STARTS_WITH_NOT_SPACE=[^\n\r\t\ \f]{1}[^\n\r]{0,7}
INSTREAM_START=\*
INSTREAM_END=\/\*


%state LINE_STARTED
%state WAITING_OPERATOR
%state WAITING_INSTREAM_OPERATOR
%state WAITING_INSTREAM_OPERATOR_SPACE

%state WAITING_SPACE_PARAMS
%state WAITING_SPACE_PARAMS_OR_INSTREAM
%state WAITING_PARAM
%state WAITING_PARAM_OR_INSTREAM
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

%state WAITING_SN
%state WAITING_SN_OR_NEW_LINE
%state WAITING_SN_OR_LINE_CONTINUES

%%

<YYINITIAL> {LINE_START}                                    { return jclBegin(LINE_STARTED, JclTypes.LINE_START); }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { return jclBegin(WAITING_SN_OR_NEW_LINE, JclTypes.COMMENT); }

<LINE_STARTED,
 WAITING_INSTREAM_OPERATOR_SPACE> {MF_IDENTIFIER_NAME}      { return jclBegin(WAITING_OPERATOR, JclTypes.OPERATOR_NAME); }

<LINE_STARTED> {SPACE}                                      { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<LINE_CONTINUED> {LINE_START}                               { return jclBegin(WAITING_SPACE_PARAMS, JclTypes.LINE_START); }

<LINE_CONTINUED> {END_OF_LINE_COMMENT}                      { return jclBegin(WAITING_LINE_CONTINUES, JclTypes.COMMENT); }

<INSTREAM_LINE_CONTINUED> {END_OF_LINE_COMMENT}             { return jclBegin(WAITING_INSTREAM_CONTINUES, JclTypes.COMMENT); }

<INSTREAM_LINE_CONTINUED> {INSTREAM_END}                    { return jclBegin(WAITING_NEW_LINE, JclTypes.INSTREAM_END); }

<INSTREAM_LINE_CONTINUED> {LINE_START}                      { return jclBegin(WAITING_INSTREAM_OPERATOR_SPACE, JclTypes.LINE_START); }

<WAITING_INSTREAM_OPERATOR_SPACE> {SPACE}                   { return jclBegin(WAITING_INSTREAM_OPERATOR, TokenType.WHITE_SPACE); }

<WAITING_INSTREAM_OPERATOR> {MF_IDENTIFIER_NAME}            { return startParams(true, JclTypes.OPERATOR); }

<INSTREAM_LINE_CONTINUED> {INSTREAM_SIMPLE_LINE}            { return jclBegin(WAITING_INSTREAM_CONTINUES, JclTypes.INSTREAM_TEXT); }


<WAITING_OPERATOR> {SPACE}                                  { return jclBegin(WAITING_OPERATOR, TokenType.WHITE_SPACE); }

<WAITING_OPERATOR> {MF_IDENTIFIER_NAME}                     { return startParams(false, JclTypes.OPERATOR); }

//<WAITING_OPERATOR> {DD_OPERATOR}                            { return jclBegin(WAITING_SPACE_PARAMS_OR_INSTREAM, JclTypes.OPERATOR); }

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

<WAITING_PARAM> {STRING}                                    { return jclBegin(WAITING_PARAM_DELIM, JclTypes.STRING); }

<WAITING_PARAM> {TUPLE}                                     { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TUPLE); }

<WAITING_PARAM> {TEMPLATE_PARAM}                            { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TEMPLATE_PARAM); }

<WAITING_PARAM> {NOT_EQUALS_NOT_SPACE}                      { return processSimpleParam(); }

<WAITING_EQUALS_OR_DELIM> {EQUALS}                          { return jclBegin(WAITING_PARAM_VALUE, JclTypes.PARAM_EQUALS); }

<WAITING_PARAM_VALUE> {STRING}                              { return jclBegin(WAITING_PARAM_DELIM, JclTypes.STRING); }

<WAITING_PARAM_VALUE> {TUPLE}                               { return jclBegin(WAITING_PARAM_DELIM, JclTypes.TUPLE); }

<WAITING_PARAM_VALUE> {NOT_SPACE}                           { return jclBegin(WAITING_PARAM_DELIM, JclTypes.SIMPLE_VALUE); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {PARAM_DELIM}    { return processParamDelim(); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {SPACE}          { return endParams(TokenType.WHITE_SPACE); }

<WAITING_PARAM_DELIM,
 WAITING_PARAM_DELIM_AFTER_INSTREAM_START> {CRLF}           { return endParams(JclTypes.CRLF); }

// TODO: think about how to rework.
//<WAITING_PARAM_DELIM> {SN_STARTS_WITH_NOT_SPACE}            { return endParams(JclTypes.SEQUENCE_NUMBERS); }


<WAITING_EQUALS_OR_DELIM> {PARAM_DELIM}                     { return processParamDelim(); }

<WAITING_EQUALS_OR_DELIM> {SPACE}                           { return endParams(TokenType.WHITE_SPACE); }

<WAITING_EQUALS_OR_DELIM> {CRLF}                            { return endParams(JclTypes.CRLF); }




<WAITING_SN> {SEQUENCE_NUMBER}                              { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_NEW_LINE> {SPACE}?{SEQUENCE_NUMBER}                { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_LINE_CONTINUES> {SPACE}?{SEQUENCE_NUMBER}          { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

<WAITING_INSTREAM_CONTINUES> {SPACE}?{SEQUENCE_NUMBER}      { return jclBegin(prevState, JclTypes.SEQUENCE_NUMBERS); }

{CRLF}+                                                     {
          if (instreamParamStarted) {
              yybegin(INSTREAM_LINE_CONTINUED);
          } else {
              yybegin(YYINITIAL);
          }
          return TokenType.WHITE_SPACE;
      }

[^]                                                         { return TokenType.BAD_CHARACTER; }
