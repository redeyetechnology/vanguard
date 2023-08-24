plugins {
    id("vanguard.platform-conventions")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    compileOnly(libs.paper.api)
    implementation(libs.cloud.paper)
    implementation(libs.bstats.bukkit)
}

paper {
    name = "Vanguard"
    main = "ltd.redeye.vanguard.paper.VanguardPaperPlugin"
    apiVersion = "1.20"
    version = "${project.version}"
    description = "Manage your server's punishments with ease."
    authors = listOf("RedEye Technologies", "FawksX", "PhilTheSkid")
    website = "https://vanguard.redeyetechnologies.co.uk"
}