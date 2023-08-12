val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

plugins {
    `java-library`
    kotlin("jvm")
    id("net.kyori.indra.publishing")
}

repositories {
    mavenCentral()
}

dependencies {

}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
}

indra {
    github("RedEyeTechnology", "vanguard") {
        ci(true)
    }
    gpl3OnlyLicense()

    configurePublications {
        pom {
            developers {
                developer {
                    id.set("RedEye Technologies Limited")
                    timezone.set("Europe/London")
                }
            }
        }
    }
}

