val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

plugins {
    `java-library`
    kotlin("jvm")
    id("org.cadixdev.licenser")
}

license {
    header(rootProject.file("HEADER"))
}

repositories {
    mavenCentral()
}

dependencies {

}