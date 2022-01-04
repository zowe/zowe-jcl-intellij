package eu.ibagroup.jcl.lang

object JclConstants {
  val OPERATORS = listOf("DD", "JOB", "EXEC", "INCLUDE", "JCLLIB", "PROC", "PEND")

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
