//STEP01 EXEC PGM=ICETOOL
//TOOLMSG  DD SYSOUT=*
//DFSMSG   DD SYSOUT=*
//IN1      DD DSN=userid.IBMMF.INPUT,DISP=SHR
//OUT1	   DD DSN=userid.IBMMF.OUTPUT,DISP=SHR
//TOOLIN   DD *
  COPY FROM(IN1) TO(OUT1) USING(CTL1)
/*
//CTL1CNTL DD *
  OPTION STOPAFT=10
/*