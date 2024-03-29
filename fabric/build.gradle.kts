import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    id("fabric-loom") version "1.3-SNAPSHOT"
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
    id("com.github.johnrengelman.shadow")
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

base.archivesName.set(project.properties["archives_base_name"] as String)
version = project.properties["mod_version"] as String
group = project.properties["maven_group"] as String

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

loom {
    splitEnvironmentSourceSets()


    runConfigs.configureEach {
        ideConfigGenerated(true)
    }

    runtimeOnlyLog4j = true
}

configurations {
    val includeLibrary by creating

    runtimeClasspath extendsFrom includeLibrary
    compileClasspath extendsFrom includeLibrary
    //configurations["main"].extendsFrom(configurations["includeLibrary"])
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_version"]}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.properties["fabric_kotlin_version"]}")
    // Uncomment the following line to enable the deprecated Fabric API modules.
    // These are included in the Fabric API production distribution and allow you to update your mod to the latest modules at a later more convenient time.

    // modImplementation("net.fabricmc.fabric-api:fabric-api-deprecated:${project.properties["fabric_version"]}")

    "includeLibrary"(project(":common")) {
        isTransitive = false
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    withType<JavaCompile> {
        options.release.set(java.targetCompatibility.majorVersion.toInt())
    }

    java {
        // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
        // if it is present.
        // If you remove this line, sources will not be generated.
        withSourcesJar()
    }

    shadowJar {
        configurations = listOf(project.configurations["includeLibrary"])
        archiveClassifier = "dev"
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
}

kotlin {
    jvmToolchain(java.targetCompatibility.majorVersion.toInt())
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}

infix fun NamedDomainObjectProvider<Configuration>.extendsFrom(conf: Configuration) {
    get().extendsFrom(conf)
}
