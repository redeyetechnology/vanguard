val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

plugins {
    id("vanguard.base-conventions")
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.cloud.core)
    compileOnly(libs.cloud.annotations)
    compileOnly(libs.cloud.minecraft.extras)
}