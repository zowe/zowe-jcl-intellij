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
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.intellij") version "1.13.0"
    java
    id("org.jetbrains.grammarkit") version "2021.2.2"
}

group = "org.zowe"
version = "0.2.0-231"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2023.1")

    // To have a dependency on zowe explorer from the marketplace
    plugins.set(listOf("org.zowe.explorer:1.1.1-231"))

    // To have a dependency on built-in plugin from \Project_dir\libs\for-mainframe
    // plugins.set(listOf("${projectDir}\\libs\\for-mainframe"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("231.8109")
        untilBuild.set("232.*")
        changeNotes.set(
            """
      <b>New features:</b>
      <ul>
        <li>Separated the plugin to three versions</li>
        <li>Zowe Explorer version update</li>
      </ul>
      <br>
      <b>Fixed bugs:</b>
      <ul>
        <li>None at the moment</li>
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
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    compileTestKotlin {
        dependsOn(compileKotlin)

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}
