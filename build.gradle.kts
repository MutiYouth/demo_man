import org.ajoberstar.grgit.Grgit
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    idea apply true

    id("java")  // Java support
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.15.0"


    // https://github.com/ajoberstar/grgit
    // JGit provides a powerful Java API for interacting with Git repositories.
    id("org.ajoberstar.grgit") version "5.2.0"
    id("com.jetbrains.python.envs") version "0.0.31"
    id("org.jetbrains.grammarkit") version "2022.3.1"
    id("com.github.ben-manes.versions") version "0.47.0"

    // alias(libs.plugins.kotlin) // Kotlin support
    // alias(libs.plugins.gradleIntelliJPlugin) // Gradle IntelliJ Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true
        generatedSourceDirs.add(file("src/main/java"))
        // excludeDirs.add(file(intellij.sandboxDirectory))
    }
}

group = properties("pluginGroup").get() // "com.weng"
version = properties("pluginVersion").get() // "0.3.0-SNAPSHOT"

repositories {
    mavenCentral()
    // jcenter()
    // https://raw.githubusercontent.com/rosjava/rosjava_mvn_repo/master
    maven("https://github.com/rosjava/rosjava_mvn_repo/master")

}
dependencies {
    implementation("org.projectlombok:lombok:1.18.28")
    // gradle-intellij-plugin doesn't attach sources properly for Kotlin :(
    // compileOnly(kotlin("stdlib-jdk8"))
    // Share ROS libraries for identifying the ROS home directory
    // Used for remote deployment over SCP
    // compile("com.hierynomus:sshj:0.27.0")
    // compile("com.jcraft:jzlib:1.1.3")

    // Useful ROS Dependencies
    // https://mvnrepository.com/artifact/org.apache.commons/com.springsource.org.apache.commons.codec
    // implementation("org.apache.commons:com.springsource.org.apache.commons.codec:1.7.0")



    // https://github.com/rosjava/rosjava_core/tags
    // https://github.com/rosjava/rosjava_mvn_repo/tree/master
    // testImplementation:为test源集添加依赖项
    // testImplementation("org.ros.rosjava_core:rosjava:0.3.6")
    // testImplementation("org.ros.rosjava_messages:std_msgs:0.5.11")
    // testImplementation("org.ros.rosjava_bootstrap:message_generation:0.3.3")
    implementation(files("plugins/demo_man/lib/rosjava-0.3.6.jar"))
    implementation(files("plugins/demo_man/lib/std_msgs-0.5.11.jar"))
    implementation(files("plugins/demo_man/lib/message_generation-0.3.3.jar"))
}

// Set the JVM language level used to build the project. Use Java 11 for 2020.3+, and Java 17 for 2022.2+.
kotlin {
    jvmToolchain(17)
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // 此处的属性项有三种设置方式(eg): 1) type.set("IC"); 2) type = "IC"; 3) type = properties("platformType")
    version = properties("platformVersion")
    type = properties("platformType")
    pluginName = properties("pluginName")
    updateSinceUntilBuild = false
    if (hasProperty("roject")) downloadSources = false

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    // plugins = properties("platformPlugins").map { it.split(',').map(String::trim).filter(String::isNotEmpty) }
    plugins.set(listOf(
        /* Plugin Dependencies */
        // "com.intellij.ideolog:193.0.15.0",  // Log file support
        // "BashSupport:1.7.13.192",           // [Ba]sh   support
        // "Docker:193.4386.10",               // Docker   support
        // "PsiViewer:201-SNAPSHOT",           // PSI view support
        // "clion-embedded",
        // "IntelliLang",
        "yaml",
        "com.intellij.clion",
        "com.intellij.cidr.base",
        "Git4Idea"
    ))
}


// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = properties("pluginRepositoryUrl")
}

// Configure Gradle Qodana Plugin - read more: https://github.com/JetBrains/gradle-qodana-plugin
qodana {
    cachePath = provider { file(".qodana").canonicalPath }
    reportPath = provider { file("build/reports/inspections").canonicalPath }
    saveReport = true
    showReport = environment("QODANA_SHOW_REPORT").map { it.toBoolean() }.getOrElse(false)
}

// Configure Gradle Kover Plugin - read more: https://github.com/Kotlin/kotlinx-kover#configuration
kover.xmlReport {
    onCheck = true
}


val userHomeDir = System.getProperty("user.home")!!
val sampleRepo = "https://github.com/duckietown/dt-core.git"
val samplePath = "${project.buildDir}/Software"

val defaultProjectPath = samplePath.let {
    if (File(it).isDirectory) it
    else it.apply {
        logger.info("Cloning $sampleRepo to $samplePath...")
        Grgit.clone(mapOf("dir" to this, "uri" to sampleRepo))
    }
}

val projectPath = File(properties["roject"] as? String ?: System.getenv()["DUCKIETOWN_ROOT"] ?: defaultProjectPath).absolutePath

val isCIBuild = hasProperty("CI")
val isPluginDev = hasProperty("luginDev")
fun prop(name: String): String = extra.properties[name] as? String ?: error("Property `$name` is not defined in gradle.properties")



tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    // wrapper {
    //     gradleVersion = properties("gradleVersion").get()
    // }

    patchPluginXml {
        sinceBuild = properties("pluginSinceBuild")
        untilBuild = properties("pluginUntilBuild")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with (it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }
        // Get the latest available change notes from the changelog file
        // changeNotes = properties("pluginVersion").map { pluginVersion ->
        //     with(changelog) {
        //         renderItem((getOrNull(pluginVersion) ?: getUnreleased())
        //                     .withHeader(false)
        //                     .withEmptySections(false),Changelog.OutputType.HTML,)
        //     }
        // }
        changeNotes = "Updates demo_man(integration Hatchery to work on 2023+. Mostly in maintenance mode now. ) "
    }

    // 需在合适的环境下，开启buildPlugin，并进行测试。
    // named("buildPlugin") {
    //     dependsOn("test")
    // }

    withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        // 在plugins/demo_man/lib下生成
        archiveFileName = "demo_man_$version.zip"
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        // token.set(System.getenv("PUBLtokenISH_TOKEN"))
        token = project.findProperty("jbr.") as String? ?: System.getenv("JBR_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = properties("pluginVersion").map { listOf(it.split('-').getOrElse(1) { "default" }.split('.').first()) }
    }

    runIde {
        // dependsOn("test")
        if (!isPluginDev && !isCIBuild) dependsOn(":build_envs")
        args = listOf(if (isPluginDev) projectDir.absolutePath else projectPath)
    }

    findByName("buildSearchableOptions")?.enabled = false

    generateLexer {
        sourceFile = File("src/main/resources/config_data/inte_hatchery/grammars/ROSInterface.flex")
        targetDir = "src/main/java/com/weng/demo_man/rosinterface"
        targetClass = "ROSInterfaceLexer"
        purgeOldFiles = true
    }

    generateParser {
        sourceFile = File("src/main/resources/config_data/inte_hatchery/grammars/ROSInterface.bnf")
        targetRoot = "src/main/java"
        pathToParser = "/com/weng/demo_man/parser/ROSInterfaceParser.java"
        pathToPsiRoot = "/com/weng/demo_man/psi"
        purgeOldFiles = true
    }

    compileKotlin {
        dependsOn(generateLexer, generateParser)
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            apiVersion = languageVersion
            freeCompilerArgs = listOf("-progressive")
        }
    }

    // Use unversioned filename for stable URL of CI build artifact
    register("copyPlugin", Copy::class) {
        from("${buildDir}/libs/demo_man_$version.zip")

        into("${project.gradle.gradleUserHomeDir}/../.CLion2020.1/config/plugins/demo_man/lib")
    }

    register("Exec clion debug suspend", Exec::class) {
        commandLine("/opt/clion/bin/clion-suspend.sh")
    }


    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    // runIdeForUiTests {
    //     systemProperty("robot-server.port", "8082")
    //     systemProperty("ide.mac.message.dialogs.as.sheets", "false")
    //     systemProperty("jb.privacy.policy.text", "<!--999.999-->")
    //     systemProperty("jb.consents.confirmation.enabled", "false")
    // }
}

envs {
    bootstrapDirectory = File(buildDir, "pythons")
    envsDirectory = File(buildDir, "envs")

    //  conda("Miniconda2", "Miniconda2-latest", listOf("numpy", "pillow"))
    // TODO: figure out how to setup conda inside the project structure
}
