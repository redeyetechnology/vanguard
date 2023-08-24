val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

plugins {
    id("vanguard.base-conventions")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.minimessage)
    compileOnly(libs.adventure.text.serializer.gson)

    implementation(libs.cloud.core)
    implementation(libs.cloud.annotations)
    implementation(libs.cloud.minecraft.extras) {
        isTransitive = false
    }
    implementation(libs.cloud.brigadier)
    implementation(libs.commodore)

    implementation(libs.morphia.core) {
        exclude("org.slf4j", "slf4j-api")
        exclude("org.testcontainers", "mongodb")
    }
    implementation(libs.morphia.kotlin){
        exclude("org.slf4j", "slf4j-api")
        exclude("org.testcontainers", "mongodb")
    }

    implementation(libs.configurate.yaml)
    implementation(libs.configurate.extra.kotlin)

    implementation(libs.redisson)
    implementation(libs.gson)

    compileOnly(libs.slf4j.api)
}

tasks {
    shadowJar {
        dependencies {
            exclude(dependency("com.mojang:brigadier"))
        }

        listOf(
            "cloud.commandframework",
            "dev.morphia",
            "com.mongodb",
            "io.github.classgraph",
            "io.leangen.geantyref",
            "io.smallrye",
            "org.spongepowered",
            "edu.umd.cs",
            "me.lucko",
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
            "org.yaml.snakeyaml",
            "org.redisson",
            "org.bstats"
        ).forEach(::reloc)

        archiveFileName.set("vanguard-${project.name}-${project.version}.jar")
    }
    build {
        dependsOn(shadowJar)
    }
}