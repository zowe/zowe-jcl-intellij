/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.jetbrains.intellij") version "1.11.0"
    java
    id("org.jetbrains.grammarkit") version "2021.2.2"
}

group = "org.zowe"
version = "0.2.0-221"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1")

    // To have a dependency on zowe explorer from the marketplace
    plugins.set(listOf("org.zowe.explorer:1.1.1-221"))

    // To have a dependency on built-in plugin from \Project_dir\libs\for-mainframe
    // plugins.set(listOf("${projectDir}\\libs\\for-mainframe"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("221.5080")
        untilBuild.set("222.*")
        changeNotes.set(
            """
      <b>New features:</b>
      <ul>
        <li>Empty string as a parameter value</li>
        <li>Comments after parameters end</li>
        <li>Lexer tests</li>
        <li>JCL Highlight init version</li>
      </ul>
      <br>
      <b>Fixed bugs:</b>
      <ul>
        <li>Error if keyword DATA is used in in-stream data declaration</li>
        <li>Error for NULL statement</li>
        <li>Operator NOT is Not recognized</li>
        <li>Unexpected error if the parameter is nullified</li>
        <li>Error if dataset member is a parameter</li>
        <li>'No such operator' for ELSE</li>
        <li>Wrong number of characters in jcl highlight line</li>
        <li>Unexpected errors for procedure declaration</li>
        <li>Unexpected error for DD override in nested PROCs</li>
        <li>Unexpected error if procedure's/job's name length=1 or name starts with national (\$, #, @) characters</li>
        <li>THEN is not highlighted</li>
        <li>Unexpected error if continue relational expression in IF/THEN/ELSE/ENDIF construction</li>
        <li>Error if there is no space between closing parentheses in the relational expression</li>
        <li>Error if parameter contains subparameters</li>
        <li>Error if string or parameter contain apostrophe</li>
        <li>Continue the interrupted parameter or field beginning in any column from 4 through 16</li>
        <li>Comma (',') after asterisk ('*') in in-stream data is marked as error</li>
        <li>Error for empty string in in-stream data</li>
        <li>Error for parentheses in accounting info in the job card</li>
        <li>'No such operator' for IF/ENDIF</li>
        <li>Error when parameters continue on the next line</li>
        <li>Error if accounting information in the job card is omitted</li>
        <li>'No such operator' for OUTPUT / EXPORT/ SET</li>
        <li>'No such operator' for CNTL / PRINTDEV / ENDCNTL</li>
        <li>PATHMODE is not highlighted</li>
        <li>'No such operator' for JOBGROUP/ENDGROUP/GJOB/AFTER/BEFORE/CONCURRENT</li>
        <li>'No such operator' for JOBSET/ENDSET/SJOB</li>
        <li>Strange parameter highlighting</li>
        <li>'No such operator' for SCHEDULE/XMIT</li>
        <li>'No such operator' for COMMAND</li>
        <li>String cannot be continued on the next line</li>
      </ul>
    """
        )
    }

    test {
        // useJUnitPlatform()
        // see https://youtrack.jetbrains.com/issue/IDEA-278926
        isScanForTestClasses = false
        include("**/*Test*")
    }
}

sourceSets {
    main {
        java.srcDirs("src/main")
        kotlin.srcDirs("src/main")
    }
    test {
        java.srcDirs("src/test")
        kotlin.srcDirs("src/test")
    }
}

val jflexVersion = "1.9.1"

grammarKit {
    // version of IntelliJ patched JFlex - https://github.com/JetBrains/intellij-deps-jflex
    jflexRelease.set(jflexVersion)
    // release version of Grammar-Kit - https://github.com/JetBrains/Grammar-Kit
    grammarKitRelease.set("2021.1.2")
}

tasks {
    generateParser {
        source.set("src/main/kotlin/org/zowe/jcl/lang/Jcl.bnf")
        targetRoot.set("src/main/java")
        pathToParser.set("/org/zowe/jcl/lang/parser/JclParser.java")
        pathToPsiRoot.set("/org/zowe/jcl/lang/psi")
        purgeOldFiles.set(true)
    }

    generateLexer {
        source.set("src/main/kotlin/org/zowe/jcl/lang/Jcl.flex")
        targetDir.set("src/main/java/org/zowe/jcl/lang")
        targetClass.set("JclLexer")
        purgeOldFiles.set(true)
    }
}

// needed until it becomes possible to set encoding of .flex file using the generateLexer task
// see https://github.com/JetBrains/gradle-grammar-kit-plugin/issues/127
val generateJclLexer = task<JavaExec>("generateJclLexer") {
    val jflexJar = "jflex-${jflexVersion}.jar"
    val source = "src/main/kotlin/org/zowe/jcl/lang/Jcl.flex"
    val targetDir = "src/main/java/org/zowe/jcl/lang"
    val encoding = "UTF-8"
    classpath = files(jflexJar)
    args("-d", targetDir, "--encoding", encoding, source)
}

tasks {
    compileKotlin {
        dependsOn(generateJclLexer, generateParser)

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}
