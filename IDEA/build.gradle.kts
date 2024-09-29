import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Java target version
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        java.srcDir("src/gen")
    }
}

kotlin {
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2.1")

        bundledPlugins(listOf("com.intellij.java"))
        instrumentationTools()

        testFramework(TestFrameworkType.Platform)
    }

    // From Kotlin documentation
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    // just in case, version number specified in buildscript is used by default
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.22")

    // IntelliJ test framework needs junit 4.
    testImplementation("junit:junit:4.13")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.0")

    // Use junit 5.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellijPlatform {
    pluginConfiguration {
        // ...

        version = "1.4.0.1"

        ideaVersion {
            sinceBuild = "242.21829.142"
            untilBuild = provider { null }
        }
    }
}

project(":") {
    val generateLexer = task<GenerateLexerTask>("generateMyLexer") {
        sourceFile.set(file("src/main/grammar/KerboScript.flex"))
        targetOutputDir.set(file("src/gen/ksp/kos/ideaplugin/parser"))
//        targetClass.set("KerboScriptLexer")
        purgeOldFiles.set(true)
    }

    val generateParser = task<GenerateParserTask>("generateMyParser") {
        sourceFile.set(file("src/main/grammar/KerboScript.bnf"))
        targetRootOutputDir.set(file("src/gen"))
        pathToParser.set("/ksp/kos/ideaplugin/parser/KerboScriptParser.java")
        pathToPsiRoot.set("/ksp/kos/ideaplugin/psi")
        purgeOldFiles.set(true)
    }

    tasks {
        withType<KotlinCompile> {
            dependsOn(generateLexer, generateParser)
        }

        // Set the compatibility versions to 17
        withType<JavaCompile> {
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }

        listOf("compileKotlin", "compileTestKotlin").forEach {
            getByName<KotlinCompile>(it) {
                kotlinOptions {
                    jvmTarget = "17"
                    freeCompilerArgs = listOf("-Xjvm-default=all")
                }
            }
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