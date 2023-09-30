import org.ajoberstar.grgit.Grgit

plugins {
    idea apply true

    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.15.0"


    // https://github.com/ajoberstar/grgit
    // JGit provides a powerful Java API for interacting with Git repositories.
    id("org.ajoberstar.grgit") version "5.2.0"
    id("com.jetbrains.python.envs") version "0.0.31"
    id("org.jetbrains.grammarkit") version "2022.3.1"
    id("com.github.ben-manes.versions") version "0.47.0"
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true
        generatedSourceDirs.add(file("src/main/java"))
        // excludeDirs.add(file(intellij.sandboxDirectory))
    }
}

group = "com.weng"
version = "0.3.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
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
    implementation("org.apache.commons:com.springsource.org.apache.commons.codec:1.7.0")
    // https://github.com/rosjava/rosjava_core/tags
    testImplementation("org.ros.rosjava_core:rosjava:0.3.6")
    testImplementation("org.ros.rosjava_messages:std_msgs:0.5.11")
    testImplementation("org.ros.rosjava_bootstrap:message_generation:0.3.3")
}


// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2.5")
    // type.set("IC") // Target IDE Platform
    type = "CL"
    pluginName = "demo_man"
    updateSinceUntilBuild = false
    if (hasProperty("roject")) downloadSources = false

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
        "com.intellij.cidr.base"
    ))
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

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
        changeNotes = "Updates demo_man(integration Hatchery to work on 2023+. Mostly in maintenance mode now. ) "
    }

    named("buildPlugin") {
        dependsOn("test")
    }

    withType<Zip> {
        archiveFileName = "demo_man.zip"
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        // token.set(System.getenv("PUBLtokenISH_TOKEN"))
        token = project.findProperty("jbr.") as String? ?: System.getenv("JBR_TOKEN")
    }

    runIde {
        // dependsOn("test")
        if (!isPluginDev && !isCIBuild) dependsOn(":build_envs")
        args = listOf(if (isPluginDev) projectDir.absolutePath else projectPath)
    }

    findByName("buildSearchableOptions")?.enabled = false

    generateLexer {
        sourceFile = File("src/main/grammars/ROSInterface.flex")
        targetDir = "src/main/java/com/weng/demo_man/rosinterface"
        targetClass = "ROSInterfaceLexer"
        purgeOldFiles = true
    }

    generateParser {
        sourceFile = File("src/main/grammars/ROSInterface.bnf")
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
        from("${buildDir}/libs/hatchery.zip")

        into("${project.gradle.gradleUserHomeDir}/../.CLion2020.1/config/plugins/hatchery/lib")
    }

    register("Exec clion debug suspend", Exec::class) {
        commandLine("/opt/clion/bin/clion-suspend.sh")
    }
}

envs {
    bootstrapDirectory = File(buildDir, "pythons")
    envsDirectory = File(buildDir, "envs")

    //  conda("Miniconda2", "Miniconda2-latest", listOf("numpy", "pillow"))
    // TODO: figure out how to setup conda inside the project structure
}
