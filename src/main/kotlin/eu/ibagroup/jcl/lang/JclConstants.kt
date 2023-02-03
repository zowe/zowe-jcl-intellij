package eu.ibagroup.jcl.lang

object JclConstants {
  val OPERATORS = listOf(
    "DD",
    "JOB",
    "EXEC",
    "INCLUDE",
    "JCLLIB",
    "PROC",
    "PEND",
    "IF",
    "THEN",
    "ELSE",
    "ENDIF",
    "OUTPUT",
    "EXPORT",
    "SET",
    "SCHEDULE",
    "XMIT",
    "JOBGROUP",
    "ENDGROUP",
    "GJOB",
    "AFTER",
    "BEFORE",
    "CONCURRENT",
    "CNTL",
    "PRINTDEV",
    "ENDCNTL",
    "JOBSET",
    "ENDSET",
    "SJOB",
    "COMMAND"
  )

  val DD_OPERATOR_PARAMS = listOf("DSN", "DISP", "DCB", "SPACE", "UNIT", "VOL", "SYSOUT")

  val EXEC_OPERATOR_PARAMS = listOf("PGM", "PROC", "PARM", "ADDRSPC", "ACCT", "TIME", "REGION", "COND")

  val JOB_OPERATOR_PARAMS = listOf(
    "CLASS",
    "PRTY",
    "NOTIFY",
    "MSGCLASS",
    "MSGLEVEL",
    "TYPRUN",
    "TIME",
    "REGION",
    "ADDRSPC",
    "BYTES",
    "LINES",
    "PAGES",
    "USER",
    "PASSWORD",
    "COND",
    "RESTART"
  )
}
