package org.stvad.kask.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceSetContainer
import java.net.URI


class KaskGeneratorGradlePlugin : Plugin<Project> {
    companion object {
        val Project.generatedOutput: Provider<Directory>
            get() = layout.buildDirectory.dir("generated")
                    .map { it.dir("source") }
                    .map { it.dir("kask") }
                    .map { it.dir("main") }
    }

    private lateinit var project: Project

    override fun apply(target: Project) {
        project = target

        val kask = createKaskTask()
        configureDependencies()
        registerGeneratedSources(kask)
    }

    private fun createKaskTask() =
            project.tasks.create("kask", Kask::class.java) {
                it.outputDirectory.set(project.generatedOutput)
            }

    private fun configureDependencies() {
        addJitpackRepository()
        addKaskDependencies()
    }

    private fun addJitpackRepository() =
            project.repositories.maven {
                it.url = URI("https://jitpack.io")
                it.name = "jitpack"
            }

    private fun addKaskDependencies() {
        addJavaPlugin()
        project.dependencies.add("implementation", "com.github.Stvad:kask:-SNAPSHOT") //todo version
    }

    private fun addJavaPlugin() = project.plugins.apply(JavaPlugin::class.java)

    private fun registerGeneratedSources(kask: Kask) {
        val sources = project.properties["sourceSets"] as SourceSetContainer

        sources.getByName("main").apply {
            output.dir(mapOf("builtBy" to kask), project.generatedOutput)
            java.srcDir(project.generatedOutput)
        }
    }
}