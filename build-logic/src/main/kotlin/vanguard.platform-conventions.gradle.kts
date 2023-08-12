val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

plugins {
    id("vanguard.base-conventions")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.minimessage)

    implementation(libs.cloud.core)
    implementation(libs.cloud.annotations)
    implementation(libs.cloud.minecraft.extras) {
        isTransitive = false
    }

    implementation(libs.morphia.core) {
        exclude("org.slf4j", "slf4j-api")
    }
    implementation(libs.morphia.kotlin){
        exclude("org.slf4j", "slf4j-api")
    }

    implementation(libs.configurate.yaml)
    implementation(libs.configurate.extra.kotlin)

}

tasks {
    shadowJar {

        listOf(
            "cloud.commandframework",
            "dev.morphia",
            "com.mongodb",
            "io.github.classgraph",
            "io.leangen.geantyref",
            "io.smallrye",
            "org.spongepowered",
            "edu.umd.cs",
            "javax.annotation",
            "net.bytebuddy",
            "nonapi.io.github.classgraph",
            "org.bson",
            "org.eclipse.microprofile.config",
            "org.intellij.lang.annotations",
            "org.jboss.logging",
            "org.jetbrains.annotations",
            "org.jsoup",
            "org.objectweb.asm",
            "org.yaml.snakeyaml"
        ).forEach(::reloc)
    }
    build {
        dependsOn(shadowJar)
    }
}