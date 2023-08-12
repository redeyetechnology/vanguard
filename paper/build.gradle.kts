plugins {
    id("vanguard.platform-conventions")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    compileOnly(libs.paper.api)
}