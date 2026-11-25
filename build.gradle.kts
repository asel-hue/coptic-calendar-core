// Bibliothèque Kotlin/JVM pure (aucune dépendance Android) : calendrier copte et modèle du jour liturgique.
// Publiée par JitPack à chaque tag : com.github.asel-hue:coptic-calendar-core:<tag>
plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
    `maven-publish`
}

group = "com.github.asel-hue"
version = "0.3.0-SNAPSHOT"

// OneDrive verrouille les dossiers build/ : sous Windows, sorties de build dans %LOCALAPPDATA%.
System.getenv("LOCALAPPDATA")?.let { local ->
    if (rootDir.absolutePath.contains("OneDrive")) layout.buildDirectory.set(File(local, "gradle-builds/coptic-calendar-core"))
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
    explicitApi()
}

java {
    withSourcesJar()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            artifactId = "coptic-calendar-core"
        }
    }
}
