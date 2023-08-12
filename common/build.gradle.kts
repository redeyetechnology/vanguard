plugins {
    id("vanguard.platform-conventions")
    kotlin("jvm") version "1.9.0"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(11)
}