# Zowe™ JCL plug-in for IntelliJ IDEA™
Plug-in for supporting JCL features in Intellij IDEA™.
The syntax highlight is available for .jcl files.

## How to use
1) Install the plug-in
2) Select any file with JCL inside. If the file has .jcl extension, the plug-in will automatically highlight the code. Otherwise, you need to select the file type manually by clicking right mouse button on the file and selecting "Override File Type". Then select "JCL"

Also, the plug-in works with USS files and z/OS datasets, so it is possible to use it with Zowe™ Explorer plug-in for IntelliJ IDEA™.

## For Developers
To start plugin you need to:
1) Generate parser via Bnf Generator from src/main/kotlin/org/zowe/jcl/lang/Jcl.bnf in src/main/java folder.
2) Generate lexer via JFlex Generator from src/main/kotlin/org/zowe/jcl/lang/Jcl.flex in src/main/java folder.

---

Zowe™, and the Zowe™ logo, and the Open Mainframe Project™ are trademarks of the Linux Foundation.
IntelliJ, IntelliJ IDEA are trademarks or registered trademarks of JetBrains, Inc.
