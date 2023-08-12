plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.minecraftforge.net/")
    maven("https://repo.jpenilla.xyz/snapshots")
}

dependencies {
    implementation(libs.kotlin)
    
    implementation(libs.indra)
    implementation(libs.indra.sonatype)

    implementation(libs.licenser)
    implementation(libs.shadow)

    compileOnly(files(libs::class.java.protectionDomain.codeSource.location))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}