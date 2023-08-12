import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

fun ShadowJar.reloc(pkg: String) {
    relocate(pkg, "ltd.redeye.vanguard.lib.$pkg")
}