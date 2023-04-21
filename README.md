# Zowe IntelliJ JCL Features
Plugin for supporting JCL features in Intellij IDEA.
The syntax highlight is available for .jcl files.

## For Developers
To start plugin you need to:
1) Generate parser via Bnf Generator from src/main/kotlin/eu/ibagroup/jcl/lang/Jcl.bnf in src/main/java folder.
2) Generate lexer via JFlex Generator from src/main/kotlin/eu/ibagroup/jcl/lang/Jcl.flex in src/main/java folder.
