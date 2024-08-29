/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

import org.jetbrains.changelog.Changelog
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)
fun dateValue(pattern: String): String =
    LocalDate.now(ZoneId.of("Europe/Warsaw")).format(DateTimeFormatter.ofPattern(pattern))

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.intellij") version "1.17.2"
    id("org.jetbrains.changelog") version "2.2.1"
    java
    id("org.jetbrains.grammarkit") version "2021.2.2"
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()
val junitJupiterVersion = "5.10.3"
val jflexVersion = "1.9.1"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2023.1")

    // To have a dependency on zowe explorer from the marketplace
    plugins.set(listOf("org.zowe.explorer:1.2.2-231"))

    // To have a dependency on built-in plugin from \Project_dir\libs\for-mainframe
    // plugins.set(listOf("${projectDir}\\libs\\for-mainframe"))
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion").get())
    header.set(provider { "${version.get()} (${dateValue("yyyy-MM-dd")})" }.get())
    groups.set(listOf("Breaking changes", "Features", "Bugfixes", "Deprecations", "Security"))
    keepUnreleasedSection.set(false)
    itemPrefix.set("*")
    repositoryUrl.set(properties("pluginRepositoryUrl").get())
    sectionUrlBuilder.set { repositoryUrl, currentVersion, previousVersion, isUnreleased: Boolean ->
        repositoryUrl + when {
            isUnreleased -> when (previousVersion) {
                null -> "/commits"
                else -> "/compare/$previousVersion...HEAD"
            }

            previousVersion == null -> "/commits/$currentVersion"
            else -> "/compare/$previousVersion...$currentVersion"
        }
    }
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }

    patchPluginXml {
        version.set("${properties("pluginVersion").get()}-${properties("sinceBuildVersion").get().substringBefore(".")}")
        sinceBuild = properties("sinceBuildVersion")
        untilBuild = properties("untilBuildVersion")

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes.set(
          properties("pluginVersion")
            .map { pluginVersion ->
                with(changelog) {
                    renderItem(
                        (getOrNull(pluginVersion) ?: getUnreleased())
                            .withHeader(false)
                            .withEmptySections(false),
                        Changelog.OutputType.HTML,
                    )
                }
            }
            .get()
        )
    }

    test {
        // useJUnitPlatform()
        // see https://youtrack.jetbrains.com/issue/IDEA-278926
        isScanForTestClasses = false
        include("**/*Test*")
    }

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

    signPlugin {
        certificateChain.set(environment("INTELLIJ_SIGNING_CERTIFICATE_CHAIN").map { it })
        privateKey.set(environment("INTELLIJ_SIGNING_PRIVATE_KEY").map { it })
        password.set(environment("INTELLIJ_SIGNING_PRIVATE_KEY_PASSWORD").map { it })
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(environment("ZOWE_INTELLIJ_MARKET_TOKEN").map { it })
        // The pluginVersion is based on the SemVer (https://semver.org)
        // Read more: https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(
          properties("pluginVersion")
            .map {
              listOf(
                it.substringAfter('-', "")
                  .substringAfter('-', "")
                  .substringBefore('.')
                  .ifEmpty { "stable" }
              )
            }
            .map { it })
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

grammarKit {
    // version of IntelliJ patched JFlex - https://github.com/JetBrains/intellij-deps-jflex
    jflexRelease.set(jflexVersion)
    // release version of Grammar-Kit - https://github.com/JetBrains/Grammar-Kit
    grammarKitRelease.set("2021.1.2")
}
