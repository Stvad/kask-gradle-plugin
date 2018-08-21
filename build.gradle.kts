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
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    implementation("org.stvad", "kask", "0.1.0")

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