plugins {
    id("vanguard.base-conventions")
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

group = "uk.co.redeyetechnologies"
version = "1.0.0"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
kotlin {
    jvmToolchain(11)
}