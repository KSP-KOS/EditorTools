import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.grammarkit") version "2020.2.1"
}

repositories {
    mavenCentral()
}

// Java target version
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    main {
        java.srcDir("src/gen")
    }
}

kotlin {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // From Kotlin documentation
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10")
    // just in case, version number specified in buildscript is used by default
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")

    // IntelliJ test framework needs junit 4.
    testImplementation("junit:junit:4.13")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.0")

    // Use junit 5.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

intellij {
    setPlugins("java")

    // Use the since build number from plugin.xml
    updateSinceUntilBuild = false
    // Keep an open until build, to avoid automatic downgrades to very old versions of the plugin
    sameSinceUntilBuild = true

    // Comment out to use the latest EAP snapshot
    version = "2020.2"
}

project(":") {
    val generateLexer = task<GenerateLexer>("generateLexer") {
        source = "src/main/grammar/KerboScript.flex"
        targetDir = "src/gen/ksp/kos/ideaplugin/parser"
        targetClass = "KerboScriptLexer"
        purgeOldFiles = true
    }

    val generateParser = task<GenerateParser>("generateParser") {
        source = "src/main/grammar/KerboScript.bnf"
        targetRoot = "src/gen"
        pathToParser = "/ksp/kos/ideaplugin/parser/KerboScriptParser.java"
        pathToPsiRoot = "/ksp/kos/ideaplugin/psi"
        purgeOldFiles = true
    }

    tasks {
        withType<KotlinCompile> {
            dependsOn(generateLexer, generateParser)
        }

        // Set the compatibility versions to 1.8
        withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
        }

        listOf("compileKotlin", "compileTestKotlin").forEach {
            getByName<KotlinCompile>(it) {
                kotlinOptions {
                    jvmTarget = "1.8"
                    freeCompilerArgs = listOf("-Xjvm-default=enable")
                }
            }
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
            options.compilerArgs.add("-Werror")
            options.compilerArgs.add("-Xlint:all")
            options.compilerArgs.add("-Xlint:-serial")
        }
    }
}