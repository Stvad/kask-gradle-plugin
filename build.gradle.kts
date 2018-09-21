import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.2.70"

    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.10.0"
}

group = "org.stvad"
version = "0.1.3"

repositories {
    jcenter()
    maven(url = "https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    implementation("com.github.Stvad:kask:-SNAPSHOT")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.9")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

gradlePlugin {
    plugins {
        create("kask") {
            id = "org.stvad.kask"
            implementationClass = "org.stvad.kask.gradle.KaskGeneratorGradlePlugin"
            displayName = "Plugin that allows you to generate a Kotlin representation of your Alexa model"
            description = "Kask plugin allows you to generate a Kotlin representation for various parts of your Alexa model (Intents, Slots, etc)."
        }
    }
}

publishing {
    repositories {
        maven(url = "$buildDir/repository")
    }
}

pluginBundle {
    website = "https://github.com/Stvad/kask"
    vcsUrl = "https://github.com/Stvad/kask-gradle-plugin"
    tags = listOf("alexa", "code-generation", "model", "kotlin", "skill")
}