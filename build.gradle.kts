plugins {
    kotlin("jvm") version "1.8.0"
}

group = "masadf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.19.0")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:4.19.0")
    testImplementation("org.seleniumhq.selenium:selenium-firefox-driver:4.19.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}