import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.intellijPlattform)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.grammarkit)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Java target version
java {
    sourceCompatibility = JavaVersion.VERSION_21
}

sourceSets {
    main {
        java.srcDir("src/gen")
    }
}

kotlin {
    java {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.3.4.1")

        testFramework(TestFrameworkType.Platform)
    }

    // From Kotlin documentation
    implementation(libs.kotlin.stdlib)
    // just in case, version number specified in buildscript is used by default
    implementation(libs.kotlin.reflect)

    // IntelliJ test framework needs junit 4.
    testImplementation(libs.junit4)
    testRuntimeOnly(libs.junit.vintage.engine)

    // Use junit 5.
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellijPlatform {
    pluginConfiguration {
        // ...

        version = "1.4.0.4"

        ideaVersion {
            sinceBuild = "243.25659.59"
            untilBuild = provider { null }
        }
    }
}

project(":") {
    val generateLexer =
        tasks.register<GenerateLexerTask>("generateMyLexer", fun GenerateLexerTask.() {
            sourceFile.set(file("src/main/grammar/KerboScript.flex"))
            targetOutputDir.set(file("src/gen/ksp/kos/ideaplugin/parser"))
            purgeOldFiles.set(true)
        })

    val generateParser = tasks.register<GenerateParserTask>("generateMyParser", fun GenerateParserTask.() {
        sourceFile.set(file("src/main/grammar/KerboScript.bnf"))
        targetRootOutputDir.set(file("src/gen"))
        pathToParser.set("/ksp/kos/ideaplugin/parser/KerboScriptParser.java")
        pathToPsiRoot.set("/ksp/kos/ideaplugin/psi")
        purgeOldFiles.set(true)
    })

    tasks {
        withType<KotlinCompile> {
            dependsOn(generateLexer, generateParser)
        }

        // Set the compatibility versions to 21
        withType<JavaCompile> {
            sourceCompatibility = "21"
            targetCompatibility = "21"
        }

        publishPlugin {
            token.set(System.getenv("KerboScript_intellijPublishToken"))
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("skipped", "failed")
    }
}

allprojects {
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:all")
            options.compilerArgs.add("-Xlint:-serial")
        }
    }
}