rootProject.name = "vanguard-build-logic"

dependencyResolutionManagement {
    repositories {
        versionCatalogs {
            this.register("libs") {
                from(files("../gradle/libs.versions.toml"))
            }
            this.register("buildSrcLibs") {
                from(files("../gradle/libs.versions.toml"))
            }
        }
    }
}