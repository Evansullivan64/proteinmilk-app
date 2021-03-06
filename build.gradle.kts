import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"

    id("org.jetbrains.dokka") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"

    application
}

group = "me.evans"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:2.1.15")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.10")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
