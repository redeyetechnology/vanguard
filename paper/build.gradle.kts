plugins {
    id("vanguard.base-conventions")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(project(":common"))
    compileOnly(libs.paper.api)
}