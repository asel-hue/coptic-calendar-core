// Bibliothèque Kotlin/JVM pure (aucune dépendance Android) : calendrier copte et modèle du jour liturgique.
// Publiée par JitPack à chaque tag : com.github.asel-hue:coptic-calendar-core:<tag>
plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
    `maven-publish`
}

group = "com.github.asel-hue"
version = "0.1.0-SNAPSHOT"

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
