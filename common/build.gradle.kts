plugins {
    id("vanguard.platform-conventions")
    kotlin("jvm") version "1.9.0"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly(libs.morphia.core)
    compileOnly(libs.morphia.kotlin)
    compileOnly(libs.configurate.yaml)
    compileOnly(libs.configurate.extra.kotlin)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.minimessage)
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(11)
}