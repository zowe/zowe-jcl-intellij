//*********************************************************************
//STEP001  EXEC PGM=SORT
//SORTIN   DD *
123456789012345 ---> Column
HEADRselect
DETAL
TRIALselect
/*
//SORTOUT  DD DSN=userid.IBMMF.OUTPUT,                                  sdf2sfsd
//        DISP=(NEW,CATLG,DELETE),
//        DCB=*.SYSUT1,SPACE=(CYL,(50,1),RLSE)                          1qwertyu
//IFBAD     IF  (ABEND | STEP1.RC > 8) THEN
//TRUE      EXEC  PROC=ERROR
//IFBADEND  ENDIF
//GOWAPCCC EXEC PARM='ENVAR("LIBPATH=/usr/lib"),
//                    MSGFILE(MYSTDERR)//bin/sh'
//SORTOUT  DD SPACE=(CYL,(50,1),RLSE)
//STEP1.RUN EXEC
//
//*********************************************************************