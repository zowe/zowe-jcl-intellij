# JCL Highlight Plugin Changelog

All notable changes to the Zowe™ JCL plug-in for IntelliJ IDEA™ will be documented in this file.

## `0.1.0 (2023-05-29)`

* Feature: Empty string as a parameter value ([e6cf5a68](https://github.com/zowe/zowe-jcl-intellij/-/commit/e6cf5a68))
* Feature: Comments after parameters end ([1c83d57c](https://github.com/zowe/zowe-jcl-intellij/-/commit/1c83d57c))
* Feature: Lexer tests ([eab3205f](https://github.com/zowe/zowe-jcl-intellij/-/commit/eab3205f))
* Feature: JCL Highlight init version ([2c82f482](https://github.com/zowe/zowe-jcl-intellij/-/commit/2c82f482))


* Bugfix: Error if keyword DATA is used in in-stream data declaration ([bcdf053f](https://github.com/zowe/zowe-jcl-intellij/-/commit/bcdf053f))
* Bugfix: Error for NULL statement ([558ac6dd](https://github.com/zowe/zowe-jcl-intellij/-/commit/558ac6dd))
* Bugfix: Operator NOT is Not recognized ([d84eda18](https://github.com/zowe/zowe-jcl-intellij/-/commit/d84eda18))
* Bugfix: Unexpected error if the parameter is nullified ([75291e8f](https://github.com/zowe/zowe-jcl-intellij/-/commit/75291e8f))
* Bugfix: Error if dataset member is a parameter ([8445b704](https://github.com/zowe/zowe-jcl-intellij/-/commit/8445b704))
* Bugfix: 'No such operator' for ELSE ([b51b87bf](https://github.com/zowe/zowe-jcl-intellij/-/commit/b51b87bf))
* Bugfix: Wrong number of characters in jcl highlight line ([b4e80362](https://github.com/zowe/zowe-jcl-intellij/-/commit/b4e80362))
* Bugfix: Unexpected errors for procedure declaration ([167522f8](https://github.com/zowe/zowe-jcl-intellij/-/commit/167522f8))
* Bugfix: Unexpected error for DD override in nested PROCs ([66d6c6d5](https://github.com/zowe/zowe-jcl-intellij/-/commit/66d6c6d5))
* Bugfix: Unexpected error if procedure's/job's name length=1 or name starts with national ($, #, @) characters ([190d724a](https://github.com/zowe/zowe-jcl-intellij/-/commit/190d724a))
* Bugfix: THEN is not highlighted ([bd669350](https://github.com/zowe/zowe-jcl-intellij/-/commit/bd669350))
* Bugfix: Unexpected error if continue relational expression in IF/THEN/ELSE/ENDIF construction ([3c118424](https://github.com/zowe/zowe-jcl-intellij/-/commit/3c118424))
* Bugfix: Error if there is no space between closing parentheses in the relational expression ([9f721c03](https://github.com/zowe/zowe-jcl-intellij/-/commit/9f721c03))
* Bugfix: Error if parameter contains subparameters ([33018837](https://github.com/zowe/zowe-jcl-intellij/-/commit/33018837))
* Bugfix: Error if string or parameter contain apostrophe ([30a25bab](https://github.com/zowe/zowe-jcl-intellij/-/commit/30a25bab))
* Bugfix: Continue the interrupted parameter or field beginning in any column from 4 through 16 ([32575dc7](https://github.com/zowe/zowe-jcl-intellij/-/commit/32575dc7))
* Bugfix: Comma (',') after asterisk ('*') in in-stream data is marked as error ([c77655a6](https://github.com/zowe/zowe-jcl-intellij/-/commit/c77655a6))
* Bugfix: Error for empty string in in-stream data ([0dac211c](https://github.com/zowe/zowe-jcl-intellij/-/commit/0dac211c))
* Bugfix: Error for parentheses in accounting info in the job card ([bf3ce24c](https://github.com/zowe/zowe-jcl-intellij/-/commit/bf3ce24c))
* Bugfix: 'No such operator' for IF/ENDIF ([efc800d1](https://github.com/zowe/zowe-jcl-intellij/-/commit/efc800d1))
* Bugfix: Error when parameters continue on the next line ([56613970](https://github.com/zowe/zowe-jcl-intellij/-/commit/56613970))
* Bugfix: Error if accounting information in the job card is omitted ([7ec42ec9](https://github.com/zowe/zowe-jcl-intellij/-/commit/7ec42ec9))
* Bugfix: 'No such operator' for OUTPUT / EXPORT/ SET ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: 'No such operator' for CNTL / PRINTDEV / ENDCNTL ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: PATHMODE is not highlighted ([59ece73e](https://github.com/zowe/zowe-jcl-intellij/-/commit/59ece73e))
* Bugfix: 'No such operator' for JOBGROUP/ENDGROUP/GJOB/AFTER/BEFORE/CONCURRENT ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: 'No such operator' for JOBSET/ENDSET/SJOB ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: Strange parameter highlighting ([bf3ce24c](https://github.com/zowe/zowe-jcl-intellij/-/commit/bf3ce24c))
* Bugfix: 'No such operator' for SCHEDULE/XMIT ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: 'No such operator' for COMMAND ([55b6bc19](https://github.com/zowe/zowe-jcl-intellij/-/commit/55b6bc19))
* Bugfix: String cannot be continued on the next line ([ca67f329](https://github.com/zowe/zowe-jcl-intellij/-/commit/ca67f329))
