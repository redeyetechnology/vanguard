rootProject.name = "vanguard"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net")
        maven("https://repo.jpenilla.xyz/snapshots")
    }
    includeBuild("build-logic")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

listOf(
    "api",
    "common",
    "paper",
    "forge",
    "fabric"
).forEach { setupProject(it.replace("/", "-"), file(it)) }

fun setupProject(name: String, projectDirectory: File) = setupProject(name) {
    projectDir = projectDirectory
}

inline fun setupProject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}