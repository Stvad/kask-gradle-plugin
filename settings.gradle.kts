rootProject.name = "kask-gradle-plugin"

includeBuild("../kask") {
    dependencySubstitution {
        substitute(module("com.github.Stvad:kask")).with(project(":"))
    }
}
