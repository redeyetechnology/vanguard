plugins {
    id("vanguard.platform-conventions")
    kotlin("jvm") version "1.9.0"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven { url = uri("https://libraries.minecraft.net/") }
}
kotlin {
    jvmToolchain(11)
}