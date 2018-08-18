import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.2.60"

    `java-gradle-plugin`
    `maven-publish`
}

group = "org.stvad"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    implementation("org.stvad", "kask", "0.1.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins.invoke {
        "kask" {
            id = "org.stvad.kask"
            implementationClass = "org.stvad.kask.gradle.KaskGeneratorGradlePlugin"
        }
    }
}

publishing {
    repositories {
        maven(url = "build/repository")
    }
}