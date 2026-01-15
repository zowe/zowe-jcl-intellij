grammar JobControlLanguage

entry JclProgram:
    job+=JobStatement 
    jobparm+=JobparmStatement?
    set+= SetStatement?
    (exec+=ExecStatement|
    (dd+=DDStatement continue+=ContinueDDStatement*)|
    set+=SetStatement|
    cond+=CondTest|
    proc+=InstreamProcStatement)*
    ;

CondTest:
    IfStatement (cond+=CondTest|(exec+=ExecStatement dd+=DDStatement* continue+=ContinueDDStatement*)|set+=SetStatement)* 
    (ElseStatement (cond+=CondTest|(exec+=ExecStatement dd+=DDStatement* continue+=ContinueDDStatement*)|set+=SetStatement)*)? 
    EndifStatement;

JobStatement:
    '//'
    name=ID
    oper='JOB' 
    param = JobParameters
    comment=ID?
    ;

JobParameters: 
    positional = PositionalJobParams
    (keyword += KeywordJobParams ((NL_CONTINUE CONTINUE_STATEMENT keyword+= KeywordJobParams) | (',' keyword+= KeywordJobParams))* );

PositionalJobParams: 
    (account = ComplexString|ID)? ','
    (programmer = ComplexString|ID)? ',';

KeywordJobParams:
    Addrspc|Bytes|Cards|Ccsid|Class|Cond|Dsenqshr|Email|Gdgbias|Group|Jeslog|Jobrc|Lines|Memlimit|Msgclass
    |Msglevel|Notify|Pages|Password|Perform|Prty|RD|Region|Restart|Seclabel|Schenv|Sysaff
    |System|Time|Typrun|Ujobcorr|User;


Bytes:
    name='BYTES' '='
    (('(' value=INT ',' ('CANCEL'|'DUMP'|'WARNING') ')') | value=INT)
    ;


Addrspc:
    name='ADDRSPC' '='
    value='VIRT'|'REAL'
    ;
Cards:
    name='CARDS' '='
    INT (',' ('CANCEL'|'DUMP'|'WARNING'))?
    ;
Ccsid:
    name='CCSID' '='
    value=INT
    ;
Cond:
    name='COND' '='
    ((value+=INT ',' Operator)| 
    ('(' value+=INT ',' Operator ')' (',' '(' value+=INT ',' Operator ')')*))
    ;
Dsenqshr:   
    name='DSENQSHR' '='
    value=('DISALLOW'|'USEJC'|'ALLOW')
    ;
Group:
    name='GROUP' '='
    '('? value=ID ')'?
    ;
Email:
    name='EMAIL' '='
    value=UserEmail;
Gdgbias:
    name='GDGBIAS' '='
    value=('JOB'|'STEP')
    ;
Jeslog:
    name='JESLOG' '='
    (('SPIN,' (value=INT ('K'|'M')?)|("'" '+'? hour=INT ':' min=INT "'"))|'NOSPIN'|'SUPPRESS')
    ;
Jobrc:
    name='JOBRC' '='
    ('MAXRC'|'LASRRC'|
    ('(STEP,' ref=[ExecStatement:ID] '.' ID ')'))
    ;
Lines:
    name='LINES' '='
    value=INT (',' ('CANCEL'|'DUMP'|'WARNING'))?
    ;
Memlimit:
    name='MEMLIMIT' '='
    ((value=INT ('K'|'M'|'G'|'T'|'P'))|'NOLIMIT')
    ;
Msgclass:
    name='MSGCLASS' '='
    value=ID
    ;
Msglevel:
    name='MSGLEVEL' '=(' ('0'|'1'|'3') ',' ('0'|'1') ')' 
    ;
Notify:
    name='NOTIFY' '='
    ('&SYSUID'| ('('? value=ID ')'?))
    ;
Pages:
    name='PAGES' '='
    value=INT (',' ('CANCEL'|'DUMP'|'WARNING'))?
    ;
Password:
    name='PASSWORD' '='
    (value=ID | ('(' valueOld=ID ',' valueNew=ID ')'))
    ;
Perform:
    name='PERFORM' '='
    value=INT
    ;
Prty:
    name='PRTY' '='
    value=INT
    ;
RD:
    name='RD' '='
    value='R'|'RNC'|'NR'|'NC'
    ;
Region:
    name='REGION' '='
    value= INT ('K'|'M')
    ;
Restart:
    name='RESTART'
    ('=*' | ('=' value= [ExecStatement:ID] '.' ID))
    ;
Seclabel:
    name='SECLABEL' '='
    value=ID
    ;
Schenv:
    name='SCHENV' '='
    value=ID
    ;
Sysaff:
    name='SYSAFF' '='
    ((value+='ANY')|
    (value+='(ANY,IND)')| 
    ('-' value+=ID)|
    ('(' '-'? value+=ID  (','  value+=ID|'*' )* ',IND'? ')'))
    
    ;
System:
    name='SYSTEM' '='
    ((value+='ANY'|'JGLOBAL'|'JLOCAL')|
    ('-'? value+=ID)|
    ('(' '-'? value+=ID  (','  value+=ID|'*' )* ')'))
    ;
Time:
    name='TIME' '='
    ('NOLIMIT'|'MAXIMUM'|'1440'|('(' min=INT? ',' sec=INT ')')|min=INT)
    ;
Typrun:
    name='TYPRUN' '='
    value='COPY'|'HOLD'|'JCLHOLD'|'SCAN'
    ;
Ujobcorr:
    name='UJOBCORR' '='
    value=ID
    ;
User:
    name='USER' '='
    '('? value=ID ')'?
    ;
Class:
    name='CLASS' '='
    value=ID
    ;

ExecStatement:
    '//'
    name=ID
    oper='EXEC'
    param = ExecParameters
    comment=ID?
    ;

ExecParameters:
    positional = PositionalExecParameter 
     ((NL_CONTINUE CONTINUE_STATEMENT keyword+= KeywordExecParameters) | (',' keyword+= KeywordExecParameters))* ;    



KeywordExecParameters:
    Acct|Addrspc|Ccsid|CondExec|Dynamnbr|Memlimit|Parm|ParmDD|Perform|RD|Region|
    Regionx|Rlstmout|Time|Tvsmsg|Tvsamcom;

PositionalExecParameter:
    (name = ('PGM' | 'PROC') '=')?
    value = ID
    ;

Acct:
    name='ACCT'
    '='
    value=ComplexString
;  
CondExec:
    name='COND'
    '='
    value+=('EVEN'|'ONLY')|
    ((value+=INT ',' Operator (',' ref+=[ExecStatement:ID])?)| 
    ('(' value+=INT ',' Operator (',' ref+=[ExecStatement:ID])? ')' (',' '(' value+=INT ',' Operator (',' ref+=[ExecStatement:ID])? ')')* 
    (',EVEN'|',ONLY')?))
    ;  
Dynamnbr:
    name='DYNAMBR'
    '='
    value=INT
;     
Parm:
    name='PARM'
    '='
    value=ComplexString
;
ParmDD:
    name='PARMDD'
    '='
    ref=[DDStatement:ID]
;

Regionx:
    name='REGIONX' '='
    (value+=INT ('K'|'M'))|
    ('(' value+=INT ('K'|'M') ',' value+=INT ('K'|'M') ')')
;
Rlstmout:
    name='RLSTMOUT'
    '='
    value=('0'|INT)
;
Tvsmsg:
    name='TVSMSG'
    '='
    value=('COMMIT'|'BACKOUT'|'ALL')
;
Tvsamcom:
    name='TVSAMCOM'
    '=('
    value+=INT ',' value+=INT ')'
;

InstreamProcStatement:
    '//'
    name=ID
    oper='PROC'
    (cond+=CondTest|(exec+=ExecStatement dd+=DDStatement* continue+=ContinueDDStatement*)|set+=SetStatement)* 
    '//'
    'PEND'
    ;

DDStatement:
    '//'
    name=ID
    oper='DD'
    param=(DDParameters | InlineDDParameters)
    comment=ID?
    ;

ContinueDDStatement:
    start=CONTINUE_STATEMENT
    oper= 'DD'
    param=DDParameters
    comment=ID?
    ;

DDParameters:
    (positional='DYNAM')|
    (positional = 'DUMMY' ',')|
    ((positional = 'DUMMY' ',')?
    (keyword += KeywordDDParameters  ((NL_CONTINUE CONTINUE_STATEMENT keyword+= KeywordDDParameters) | (',' keyword+= KeywordDDParameters))* ))
    ;    


InlineDDParameters:
    name=INLINE_DD 
    value+=INLINE_DD_VALUE+;


KeywordDDParameters:Accode|Amp|Avgrec|Blksize|Blkszlim|Burst|
    Ccsid|Chars|Chkpt|Cntl|Copies|Dataclas|Dcb|DDname|Dest|
    Disp|Dlm|Dsid|Dskeylbl|Dsname|Dsntype|Eattr|
    Expdt|Fcb|Filedata|Flash|Free|Freevol|Gdgorder|Hold|Keylabl1|
    Keylabl2|Keyencd1|Keyencd2|Keylen|Keyoff|Label|Lgstream|
    Like|Lrecl|Maxgens|Mgmtclas|Modify|Nullovrd|Outlim|Output|
    Path|PathDisp|PathMode|PathOpts|Protect|RecfmDD|Reorg|RefDD|
    Retpd|Rls|RoAccess|SecModel|Segment|Space|Spin|StorClass|
    SubSys|Symbols|SymList|Sysout|Term|Ucs|Unit|Volume;

Accode:
    name='ACCODE'
    '='
    value=ID;

Amp:
    name='AMP'
    '='
    ('(' value+=AmpSubParameter ')' )|
    ("'" value+=AmpSubParameter (',' value+=AmpSubParameter)* "'" )|
    ("(" "'" value+=AmpSubParameter (',' value+=AmpSubParameter)* "'" (',' "'" value+=AmpSubParameter (',' value+=AmpSubParameter)* "'")* ")")
    ;
AmpSubParameter:Accbias|Amorg|Bufnd|Bufni|Bufsp|Crops|Frlog|Msg|
    Optcd|Recfm|Rmode31|Smbdfr|Smbhwt|Smbvsp|Smbvspi|Strno|Synad|Trace;

Accbias:
    name='ACCBIAS'
    '='
    value='USER'|'SYSTEM'|'DO'|'DW'|'SO'|'SW'
    ;
Amorg: 
    name='AMORG'
    ;
Bufnd:
    name='BUFND'
    '='
    value=INT
    ;
Bufni:
    name='BUFNI'
    '='
    value=INT
    ;
Bufsp:
    name='BUFSP'
    '='
    value=INT
    ;
Crops:
    name='CROPS'
    '='
    value='RCK'|'NCK'|'NRE'|'NRC'
    ;
Frlog:
    name='FRLOG'
    '='
    value='NONE'|'REDO'
    ;
Msg:
    name='MSG'
    '=SMBBIAS'
    ;

Optcd:
    name='OPTCD'
    '='
    value='I'|'L'|'IL'
    ;
Recfm:
    name='RECFM'
    '='
    value='F'|'FB'|'V'|'VB'
    ;
Rmode31:
    name='RMODE31'
    '='
    value='ALL'|'BUFF'|'CB'|'None'
    ;
Smbdfr:
    name='SMBDFR'
    '='
    value='Y'|'N'
    ;
Smbhwt:
    name='SMBHWT'
    '='
    value=INT
    ;
Smbvsp:
    name='SMBVSP'
    '='
    value=INT ('K'|'M')
    ;
Smbvspi:
    name='SMBVSPI'
    '='
    value=INT ('K'|'M')
    ;
Strno:
    name='STRNO'
    '='
    value=INT
    ;
Synad:
    name='SYNAD'
    '='
    value=ID
    ;
Trace:
    name='TRACE'
    ;

Avgrec:
    name='AVGREC'
    '='
    value='U'|'K'|'M'
    ;
    
Blksize:
    name='BLKSIZE'
    '='
    value=INT ('K'|'M'|'G')?
    ;
    
Blkszlim:
    name='BLKSZLIM'
    '='
    value=INT ('K'|'M'|'G')?
    ;
    
Burst:
    name='BURST'
    '='
    value='Y'|'YES'|'N'|'NO'
    ;
    
Chars:
    name='CHARS'
    '='
    ('DUMP')|
    ('(DUMP' (',' value+=ID)* ')')|
    value+=ID|
    ( '(' value+=ID (',' value+=ID)* ')' )
    ;
    
Chkpt:
    name='CHKPT'
    '='
    value='EOV'
    ;
    
Cntl:
    name='CNTL'
    '='
    value='??????'
    ;
    
Copies:
    name='COPIES'
    '='
    (value+=INT)|
    ('(' value+=INT? ',' '(' value+=INT  (',' value+=INT )*')')
    ;
    
    
Dataclas:
    name='DATACLAS'
    '='
    value=ID
    ;
    
Dcb:
    name='DCB'
    '='
    value='???'
    ;
    
DDname:
    name='DDNAME'
    '='
    value=ID
    ;
    
Dest:
    name='DEST'
    '='
    value='????'
    ;

Disp:
    name='DISP'
    '='
    value=Status|
    ('(' status= Status? ',' normal= NormalTerm (',' abnormal= AbnormalTerm)? ')')
    ;

Status returns string:
    'NEW'|'OLD' |'SHR'|'MOD';

NormalTerm returns string:
    'DELETE'|'KEEP'|'PASS'|'CATLG';

AbnormalTerm returns string:
    'DELETE'|'KEEP'|'PASS'|'CATLG'|'UNCATLG';

Dlm:
    name='DLM'
    '='
    value=ID
    ;
    
Dsid:
    name='DSID'
    '='
    (value=ID)| ('(' value=ID ',V)')
    ;
    
Dskeylbl:
    name='DSKEYLBL'
    '='
    '(' value=ID ')'
    ;
    
Dsname:
    name=('DSNAME'|'DSN')
    (('=' '&&'? value+=ID ('.' value+=ID)* '(' ((member=ID)| ('&' set=[SetParameter:ID])) ')')|
    ('=' '&&'? value+=ID ('.' value+=ID)*)|
    ('=' value+='NULLFILE')|
    ('=*.' dd+=[DDStatement:ID]))
    ;
    
Dsntype:
    name='DSNTYPE'
    '='
    value='LIBRARY'|'(LIBRARY,1)'|'(LIBRARY,2)'|'HFS'|'PDS'|'PIPE'|'EXTREQ'|'(EXTREQ,1)'|
    '(EXTREQ,2)'|'EXTPREF'|'(EXTPREF,1)'|'(EXTPREF,2)'|'LARGE'|'BASIC'
    ;

    
Eattr:
    name='EATTR'
    '='
    value='OPT'|'NO'
    ;

Expdt:
    name='EXPDT'
    '='
    value+=INT ('/' value+=INT)? 
    ;
    
Fcb:
    name='FCB'
    '='
    value=ID|
    ('(' value=ID (',ALIGN'|',VERIFY')? ')')
    ;
    
Filedata:
    name='FILEDATA'
    '='
    value='BINARY'|'TEXT'|'RECORD'
    ;
    
Flash:
    name='FLASH'
    '='
    value=ID|
    value='NONE'|
    ('(' value=ID (',' count=INT )? ')')
    ;
    
Free:
    name='FREE'
    '='
    value='END'|'CLOSE'
    ;
    
Freevol:
    name='FREEVOL'
    '='
    value='EOV'|'END'
    ;
    
Gdgorder:
    name='GDGORDER'
    '='
    value='USERCATLG'|'LIFO'|'FIFO'
    ;
    
Hold:
    name='HOLD'
    '='
    value='YES'|'Y'|'NO'|'N'
    ;
    
Keylabl1:
    name='KEYLABL1'
    '='
    "'"? value+=ID ('.' value+=ID)* "'"?
    ;

Keylabl2:
    name='KEYLABL2'
    '='
    "'"? value+=ID ('.' value+=ID)* "'"?
    ;
    
Keyencd1:
    name='KEYENCD1'
    '='
    value='L'|'H'
    ;
    
Keyencd2:
    name='KEYENCD2'
    '='
    value='L'|'H'
    ;
    
Keylen:
    name='KEYLEN'
    '='
    value=INT
    ;
    
Keyoff:
    name='KEYOFF'
    '='
    value=INT
    ;
    
Label:
    name='LABEL'
    '='
    '(' dsnNumber=INT? ((',' ('AL'|'AUL'|'BLP'|'LTM'|'NL'|'NSL'|'SL'|'SUL')?) 
    (',' 'PASSWORD'? (',' 'IN')?)?) (',' retpd=INT ')')? 
    ;
    
Lgstream:
    name='LGSTREAM'
    '='
    value=ID
    ;

Like:
    name='LIKE'
    '='
    value='???'
    ;
    
Lrecl:
    name='LRECL'
    '='
    value=INT
    ;
    
Maxgens:
    name='MAXGENS'
    '='
    value=INT
    ;
    
Mgmtclas:
    name='MGMTCLAS'
    '='
    value=ID
    ;
    
Modify:
    name='MODIFY'
    '='
    value=ID |
    ('(' value=ID (',' trc=INT)? ')')
    ;
    
Nullovrd:
    name='NULLOVRD'
    ;
    
Outlim:
    name='OUTLIM'
    '='
    value=INT
    ;
    
Output:
    name='OUTPUT'
    '='
    value+=ID (',' value+=ID)* '???????'
    ;

Path:
    name='PATH'
    '='
    value='??????'
    ;
    
PathDisp:
    name='PATHDISP'
    '='
    value='???????'
    ;
    
PathMode:
    name='PATHMODE'
    '='
    value='??????'
    ;
    
PathOpts:
    name='PATHOPTS'
    '='
    value='?????'
    ;
    
Protect:
    name='PROTECT'
    '='
    value='????????'
    ;
    
RecfmDD:
    name='RECFM'
    '='
    value='U'|'F'|'FT'|'V'|'VS'|'VBS'
    ;
    
Reorg:
    name='REORG'
    '='
    value='??????'
    ;
    
RefDD:
    name='REFDD'
    '='
    value='??????'
    ;

Retpd:
    name='RETPD'
    '='
    value='???????'
    ;
    
Rls:
    name='RLS'
    '='
    value='?????'
    ;
    
RoAccess:
    name='ROACCESS'
    '='
    value='???????'
    ;
    
SecModel:
    name='SECMODEL'
    '='
    value='?????'
    ;
    
Segment:
    name='SEGMENT'
    '='
    value='?????'
    ;
    
Space:
    name='SPACE'
    '='
    value='???????'
    ;
    
Spin:
    name='SPIN'
    '='
    value='?????'
    ;
    
StorClass:
    name='STORCLASS'
    '='
    value='?????'
    ;

SubSys:
    name='SUBSYS'
    '='
    value='????????'
    ;
    
Symbols:
    name='SYMBOLS'
    '='
    value='???????'
    ;
    
SymList:   
    name='SYMLIST'
    '='
    value='??????'
    ;
    
Sysout:
    name='SYSOUT'
    '=*'
    ;
    
Term:
    name='TERM'
    '='
    value='TS'
    ;
    
Ucs:
    name='UCS'
    '='
    value= CharSetCode
    (',' 'FOLD'?  (',' 'VERIFY')?)
    ;

CharSetCode returns string: 'AN'|'A11'|'HN'|'H11'|'G11'|'PCAN'|'PCHN'|'PN'|'P11'|
    'QN'|'QNC'|'RN'|'SN'|'TN'|'XN'|'YN'  ;

Unit:
    name='UNIT'
    '='
    ('(' value=INT (',' unit=INT? (',' 'DEFER'? (',' 'SMSHONOR')?)?)? ')')|
    ( '(' '/' value=INT (',' 'P')? ',' ')')|
    (value=ID)|
    ('AFF=' value=ID)
    ;
    
Volume:
    name=('VOLUME='|'VOL=')
    ('PRIVATE'|
    ( ('(' 'PRIVATE'? ',' 'RETAIN'? ',' seqNumber=INT? ',' count=INT? ',')? 
    (('SER=' value+=ALFANUM) |
    ('SER=(' value+=ALFANUM (',' value+=ALFANUM)+ ')')|
    ('REF=' (("'" value+=ALFANUM "'")|
    ('*.' refDD+=[DDStatement:ID] ('.' refExec=[ExecStatement:ID] ('.' procStep=ID)?)?)|
    ('*.' RefDD+=[DDStatement:ID] '.' refProc=[InstreamProcStatement:ID] '.' refProcStep=[ExecStatement:ID])
    )))))
    ;

SetStatement:
    '//'
    name=ID?
    oper='SET'
    param=SetParameters
    comment = ID?
    ;

SetParameters:
     (param+=SetParameter ((NL_CONTINUE CONTINUE_STATEMENT param+=SetParameter) | (',' param+=SetParameter))* );    


SetParameter:
    name=ID '=' value=ComplexString;

JobparmStatement:
    '/*'
    'JOBPARM'
    param=JobparmParameters
    ;

JobparmParameters:
    BurstParam|BytesParam|CardsParam|CopiesParam|FormsParam|
    LinectParam|LinesParam|NologParam|PagesParam|ProclibParam|
    RestartParam|RoomParam|SysaffParam|TimeParam;

BurstParam infers Jobparm:
    param=('BURST'|'B') '=' value=('Y'|'N');

BytesParam infers Jobparm:
    param=('BYTES'|'M') '=' value=INT;

CardsParam infers Jobparm:
    param=('CARDS'|'C') '=' value=INT;

CopiesParam infers Jobparm:
    param=('COPIES'|'N') '=' value=INT;

FormsParam infers Jobparm:
    param=('FORMS'|'F') '=' value=(ID|'STD');

LinectParam infers Jobparm:
    param=('LINECT'|'K') '=' value=INT;

LinesParam infers Jobparm:
    param=('LINES'|'L') '=' value=INT;

NologParam infers Jobparm:
    param=('NOLOG'|'J');

PagesParam infers Jobparm:
    param=('PAGES'|'G') '=' value=INT;

ProclibParam :
    param=('PROCLIB'|'P') '=' value=[DDStatement:ID];

RestartParam infers Jobparm:
    param=('RESTERT'|'E') '=' value=('Y'|'N');

RoomParam infers Jobparm:
    param=('ROOM'|'R') '=' value=ID;

SysaffParam infers Jobparm:
    param=('SYSAFF'|'S') '=*';

TimeParam infers Jobparm:
    param=('TIME'|'T') '=' value=INT;


ElseStatement:
    ('//' name=ID | start=CONTINUE_STATEMENT )
    oper='ELSE'
    comment = ID?;


EndifStatement:
    ('//' name=ID | start=CONTINUE_STATEMENT )
    oper='ENDIF'
    comment = ID?;


IfStatement: 
    (start=CONTINUE_STATEMENT | '//' name=ID )
    oper+='IF'
    expr=Expression
    oper+='THEN'
    comment = ID?
    ;


Expression : AndExpression;

AndExpression infers Expression: OrExpression 
    ({ infer AndExpression.left=current} 
    op=('AND' | '&' | AndExprContinue) 
    right=OrExpression)*;

OrExpression infers Expression: SimpleExpression 
    ({ infer OrExpression.left=current} 
    op=('OR' | '|' | OrExprContinue) 
    right=SimpleExpression)*;

SimpleExpression infers Expression:
    '(' Expression ')'|
    NotExpression|
    RCExpression |
    RunExpression |
    AbendExpression |
    AbendCCExpression
    ;

RCExpression infers RelationalExpression:
    (ref=[ExecStatement:ID] '.')? name='RC' oper=ExprComparisonOperator value=INT;

RunExpression infers RelationalExpression: 
    name='RUN' oper=ExprComparisonOperator value=('TRUE'|'FALSE');

AbendExpression infers RelationalExpression: 
    (ref=[ExecStatement:ID] '.')? name='ABEND' oper=ExprComparisonOperator value=('TRUE'|'FALSE');

AbendCCExpression infers RelationalExpression:
    (ref=[ExecStatement:ID] '.')? name='ABENDCC' oper=ExprComparisonOperator value=AbendCCCode;

NotExpression: op=('¬'|'NOT') expr=Expression;
    

ExprComparisonOperator returns string: 'GT' | 'LT' | 'NG' | 'NL' | 'EQ' | 'NE' |'GE' | 'LE' |
    '>' | '<' | '¬>' | '¬<' | '=' | '¬=' | '>=' | '<=';

Operator returns string: 'GT'|'GE'|'EQ'|'LT'|'LE'|'NE';

UserEmail returns string: ID '@' ID '.' ID;

AndExprContinue returns string: AND_EXPRESSION_CONTINUE CONTINUE_STATEMENT;
OrExprContinue returns string: OR_EXPRESSION_CONTINUE CONTINUE_STATEMENT;

ComplexString returns string: ('"'|'{'|"'"|'(')+ ID ((','|'.'|'/')? ID)* ('"'|'}'|"'"|')')+; 

AbendCCCode returns string: 'U' INT|'S' ID;
terminal INT: /\d+/ ;
terminal ID: /[$@#_a-zA-Z][\w_$@#]*/;
terminal ALFANUM: /[$@#\w0-9]+/;
terminal INLINE_DD: /(\*|(DATA))\s*\r?\n/;
terminal INLINE_DD_VALUE: /%+\r?\n/;
hidden terminal WS: /\s+/;
hidden terminal SL_COMMENT: /\/\/\*.{0,69}/;
terminal CONDITIONAL_EXPRESSION_CONTINUE: /\/\/\s{4}/;
hidden terminal EMPTY_STATEMEMT: /\/\*\s*\r?\n/;
terminal CONTINUE_STATEMENT: /\/\/\s+/;
terminal STR_BEGIN: /"|{|'|\(/;
terminal STR_END: /"|}|'|\)/;
terminal AND_EXPRESSION_CONTINUE: /(AND|&)\s*\r?\n/;
terminal OR_EXPRESSION_CONTINUE: /(OR|\|)\s*\r?\n/;
terminal NL_CONTINUE: /,\s*\r?\n/;
terminal EXPR_CONTINUE: /OR\s*\r?\n/;